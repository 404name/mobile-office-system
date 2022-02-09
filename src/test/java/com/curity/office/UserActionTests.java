package com.curity.office;

import com.curity.office.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.erupt.upms.base.LoginModel;

@SpringBootTest
class UserActionTests {

    @Autowired
    private UserService userService; //使用方式与 mybatis-plus 大同小异
    @Test
    void login(){
        LoginModel login = userService.login("erupt", "erupt");
        System.out.println(login.toString());
        System.out.println(login.getReason());
    }
}
