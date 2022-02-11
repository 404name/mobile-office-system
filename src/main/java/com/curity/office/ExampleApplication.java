package com.curity.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import xyz.erupt.core.annotation.EruptScan;

import java.awt.*;
import java.net.URI;

// TODO： 接入微信WXJAVA ： {https://github.com/binarywang/weixin-java-miniapp-demo/blob/master/pom.xml}
//@EruptAttachmentUpload(xxxxxx.class)
@SpringBootApplication
@EntityScan
@EruptScan
public class ExampleApplication extends SpringBootServletInitializer {

    //详细使用方法详见项目内 README.md 文件说明
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
        try {
            System.setProperty("java.awt.headless", "true");
            //开启后跳转
            //Desktop.getDesktop().browse(new URI("http://localhost:8080"));
        } catch (Exception e) {
            System.setProperty("java.awt.headless", "true");
        }
        System.err.println("详细使用方法，请阅读：README.md");
    }

}
