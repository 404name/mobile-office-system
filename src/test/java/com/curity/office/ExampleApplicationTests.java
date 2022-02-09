package com.curity.office;

import com.curity.office.dao.DocumentRepository;
import com.curity.office.model.Document;
import com.curity.office.workflow.ApprovalStatus;
import com.curity.office.workflow.StateMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ExampleApplicationTests {

    @Autowired
    private DocumentRepository documentRepository; //使用方式与 mybatis-plus 大同小异

    //获取所有文章
    @Test
    void findArticleList() {
        for (Document document : documentRepository.findAll()) {
            System.out.println(document.toString());
        }
    }

    //根据标题获取文章
    @Test
    void findArticleByTitle() {
        for (Document document : documentRepository.findAll()) {
            System.out.println(document.toString());
        }
    }

    //删除
    @Test
    void deleteArticle() {
//        articleRepository.deleteById(1L);
        Document document = documentRepository.getById((long) 1);
        System.out.println(document);
        ApprovalStatus[] values = ApprovalStatus.values();
        Integer state1 = Integer.parseInt(document.getState());
        StateMachine stateMachine = new StateMachine(values[1],true);
        stateMachine.doWork();
        ApprovalStatus state = (ApprovalStatus)stateMachine.getState();
        System.out.println("切换状态" + state.toString());

        stateMachine = new StateMachine(values[state1],true);
        stateMachine.doWork();
        state = (ApprovalStatus)stateMachine.getState();
        System.out.println("切换状态" + state.toString());
    }

    //新增 or 更新
    @Test
    void saveArticle() {
        Document document = new Document();
        document.setContent("123");
        document.setTitle("123");
        document.setCode("123");
        document.setDate(new Date());
        document.setEstate("0");
        document.setPublisher((long) 1);
        documentRepository.save(document);
    }


}
