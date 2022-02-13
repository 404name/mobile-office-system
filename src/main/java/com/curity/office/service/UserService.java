package com.curity.office.service;

import xyz.erupt.upms.base.LoginModel;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.vo.AdminUserinfo;

/**
 * @program: erupt-example
 * @description: 用户相关
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 16:53
 **/
public interface UserService {
    /**
     * 获取当前用户
     * @return
     */
    EruptUser update(EruptUser eruptUser);
    /**
     * 获取当前用户
     * @return
     */
    AdminUserinfo currentUser();

    /**
     * 第三方登录/注册 自动绑定用户
     * @return
     */
    LoginModel login(String account, String pwd);
}
