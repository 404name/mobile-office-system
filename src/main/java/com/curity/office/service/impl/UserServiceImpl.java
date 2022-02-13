package com.curity.office.service.impl;

import com.curity.office.dao.DocumentHistoryRepository;
import com.curity.office.dao.DocumentRepository;
import com.curity.office.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.base.LoginModel;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;
import xyz.erupt.upms.vo.AdminUserinfo;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 17:20
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    EruptUserService eruptUserService;
    @Autowired
    DocumentHistoryRepository documentHistoryRepository;
    @Autowired
    EruptDao eruptDao;

    @Override
    public EruptUser update(EruptUser eruptUser) {
        return eruptDao.merge(eruptUser);
    }

    @Override
    public AdminUserinfo currentUser() {

        AdminUserinfo adminUserinfo = this.eruptUserService.getAdminUserInfo();
        return adminUserinfo;
    }

    @Override
    public LoginModel login(String account, String pwd) {
        //使用erupt的
        return null;
    }
}
