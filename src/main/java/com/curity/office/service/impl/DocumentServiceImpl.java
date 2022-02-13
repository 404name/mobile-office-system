package com.curity.office.service.impl;

import com.curity.office.dao.AuthUserRepository;
import com.curity.office.dao.DocumentHistoryRepository;
import com.curity.office.dao.DocumentRepository;
import com.curity.office.model.Document;
import com.curity.office.model.DocumentHistory;
import com.curity.office.service.DocumentService;
import com.curity.office.workflow.ApprovalStatus;
import com.curity.office.workflow.StateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.service.EruptUserService;
import xyz.erupt.upms.vo.AdminUserinfo;

import java.util.*;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 17:19
 **/
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentHistoryRepository documentHistoryRepository;
    @Autowired
    EruptUserService eruptUserService;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    EruptDao eruptDao;

    @Override
    public Document getById(Long id) {
        return documentRepository.getById(id);
    }

    @Override
    public Document add(Document document) {
        String state = document.getState();
        if(state.equals("0")|| state.equals("1")){

            DocumentHistory history = new DocumentHistory();
            history.setApprover(document.getPublisher());
            history.setApproverName(document.getPublisherName());
            history.setPublish(true);
            history.setDate(new Date());
            history.setState("0");
            if(state.equals("0")){
                history.setRemark("创建公文(草稿未提交)");
            }else  if(state.equals("1")) {
                history.setRemark("提交了公文审批");
            }
            // TODO: 查找用户名
            String img = "";
            // 两种方案，按照authUser查，直接按照 id拼接获取url图片
            history.setPic("/2022-02-08/KtYeuOvNajhQ.jpg");
            Set<DocumentHistory> documentHistory = document.getDocumentHistory();
            documentHistory.add(history);
            document.setDocumentHistory(documentHistory);
            System.out.println(documentHistory);
        }
        return documentRepository.save(document);
    }

    @Override
    public Document upadte(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public List<Document> findAll() {
        return null;
    }

    @Override
    public List<Document> getPublish() {
        AdminUserinfo adminUserinfo = this.eruptUserService.getAdminUserInfo();
        return documentRepository.findAllByState(Integer.toString(ApprovalStatus.publish.ordinal()));
    }
    @Override
    public List<Document> getMyDocument() {
        AdminUserinfo adminUserinfo = this.eruptUserService.getAdminUserInfo();
        return documentRepository.findAllByPublisher(adminUserinfo.getId());
    }
    @Override
    public List<Document> getUnProcessed() {
        AdminUserinfo adminUserinfo = this.eruptUserService.getAdminUserInfo();
        String post = adminUserinfo.getPost();
        /**
         * @post 用户职责
         * 管理员获取除了0 和 6的所有
         * 0 草稿 --> 员工自己 employee
         * 1 部门审核 --> 部门领导 departmentHead 审核自己部门人的
         * 2 办公室秘书审核 --> officeSecretary
         * 3 办公室主任审核 --》 officeDirector
         * 4 公司领导审核 --》 companyLeader
         * 5 秘书发行 --》 officeSecretary
         * 6 发行成功 --》 全体可见。。
         */
        List<Document> documents = null;
        documents  =  documentRepository.findAllByPublisherAndState(adminUserinfo.getId(),ApprovalStatus.draft.getStateCode());
        if(adminUserinfo.isSuperAdmin() == true){
            documents.addAll(documentRepository.findAllByStateNotIn(
                    Arrays.asList(ApprovalStatus.draft.getStateCode(), ApprovalStatus.publish.getStateCode())));
        }else if(post.equals("employee")){
            //前面已经获取自己的草稿

        }
        else if(post.equals("departmentHead")){
            //TODO 暂时只获取所有人
            documents.addAll(documentRepository.findAllByState(ApprovalStatus.department_check.getStateCode()));
            //只能获取本部门的
        }
        else if(post.equals("officeSecretary")){
            //获取初审和发行的
            documents.addAll(documentRepository.findAllByStateIn(
                    Arrays.asList(ApprovalStatus.first_trial.getStateCode(), ApprovalStatus.proofread.getStateCode())));
        }
        else if(post.equals("officeDirector")){
            documents.addAll(documentRepository.findAllByState(ApprovalStatus.recheck.getStateCode()));
        }else if(post.equals("companyLeader")){
            documents.addAll(documentRepository.findAllByState(ApprovalStatus.leader_recheck.getStateCode()));
        }
        return documents;
    }


    @Override
    public List<Map<String, Object>> getProcessed() {
        AdminUserinfo adminUserinfo = this.eruptUserService.getAdminUserInfo();
        List<Map<String, Object>> list1 = eruptDao.getJdbcTemplate()
                .queryForList("select * from office_document_history where approver = " + adminUserinfo.getId());
        return list1;
    }

    @Override
    public Document approval(Long documentId,boolean pass,String remark,Long notification){
        AdminUserinfo adminUserInfo = eruptUserService.getAdminUserInfo();
        Long approvalerId = adminUserInfo.getId();
        Document document = documentRepository.getById(documentId);
        // 记录历史记录
        Set<DocumentHistory> documentHistory = document.getDocumentHistory();
        DocumentHistory history = new DocumentHistory();
        history.setApprover(approvalerId);
        history.setPublish(pass);
        history.setDate(new Date());
        history.setState(document.getState());
        if(remark == "" || remark == null){
            if(adminUserInfo.isSuperAdmin() == true) {
                remark = "管理员通过";
            } else {
                remark = adminUserInfo.getPost() + "默认通过";
            }
        }
        history.setRemark(remark);
        // TODO: 查找用户名
        history.setApproverName(adminUserInfo.getUsername());
        String img = "";
        // 两种方案，按照authUser查，直接按照 id拼接获取url图片
        authUserRepository.findByUserId(adminUserInfo.getId());
        history.setPic("/2022-02-08/KtYeuOvNajhQ.jpg");
        documentHistory.add(history);
        document.setDocumentHistory(documentHistory);
        // TODO: notification发送通知

        //根据是否通过 转换状态
        StateMachine stateMachine = new StateMachine(ApprovalStatus.values()[Integer.parseInt(document.getState())],pass);
        stateMachine.doWork();
        ApprovalStatus state = (ApprovalStatus)stateMachine.getState();
        System.out.println("切换状态" + state.toString());
        document.setState(Integer.toString(state.getState()));
        //保存状态
        Document save = documentRepository.save(document);
        System.out.println(save);
        return save;
    }
}
