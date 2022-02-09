package com.curity.office;

import com.curity.office.common.Result;
import com.curity.office.controller.AuthController;
import com.curity.office.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.erupt.upms.base.LoginModel;

@SpringBootTest
class UserActionTests {

    @Autowired
    private AuthController authController; //使用方式与 mybatis-plus 大同小异
    @Test
    void login(){
        Result login = authController.login("erupt", "erupt","123");
        System.out.println(login);
    }
}
