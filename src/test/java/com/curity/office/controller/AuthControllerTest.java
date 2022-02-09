package com.curity.office.controller;

import com.curity.office.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-09 15:03
 **/
@SpringBootTest
class AuthControllerTest {
    @Autowired
    AuthController authController;

    @Test
    void register() {
        Result register = authController.register("test", "test", "t");
        System.out.println(register);
    }
}
