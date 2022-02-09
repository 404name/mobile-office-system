package com.curity.office.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-09 11:49
 **/
@SpringBootTest
class DocumentControllerTest {

    @Autowired
    DocumentController documentController;

    @Test
    void approval() {
        System.out.println(documentController.approval((long) 1,true,"test",(long)1));
    }

    @Test
    void getById() {

        System.out.println(documentController.getById((long) 1));
    }
}
