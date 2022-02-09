package com.curity.office.service;

import com.curity.office.model.Document;
import com.curity.office.model.DocumentHistory;

import java.util.List;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 16:53
 **/
public interface DocumentService {
    /**
     * 获取所有
     * @return
     */
    Document getById(Long id);
    /**
     * 获取所有
     * @return
     */
    List<Document> findAll();
    /**
     * 获取所有发布的
     * @return
     */
    List<Document> getPublish();
    /**
     * 获取个人发布的所有
     * @return
     */
    List<Document> getMyDocument();
    /**
     * 获取待处理的
     * @return
     */
    List<Document> getUnProcessed();
    /**
     * 获取审批历史
     * @return
     */
    List<DocumentHistory> getProcessed();
    /**
     * 审批流程
     * @param documentId 审批公文id
     * @param pass 是否通过
     * @param remark 备注
     * @param notification 通知方式
     * @return
     */
    Document approval(Long documentId,boolean pass,String remark,Long notification);
}
