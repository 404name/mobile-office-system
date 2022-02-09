package com.curity.office.controller;

import com.curity.office.common.Result;
import com.curity.office.service.DocumentService;
import com.curity.office.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 21:22
 **/
@RestController //使用此注解实现依赖注入相关功能（可选）
@RequestMapping("/erupt-api/office")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/approval")
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public Result approval(@RequestParam("documentId") Long documentId,
                           @RequestParam("pass") boolean pass,
                           @RequestParam("remark") String remark,
                           @RequestParam("notification") Long notification){
        Document approval = documentService.approval(documentId, pass, remark, notification);
        return Result.success(approval);
    }
    @GetMapping("/document")
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public Result getById(@RequestParam("documentId") Long documentId){
        Document approval = documentService.getById(documentId);
        return Result.success(approval);
    }

    @GetMapping("/getPublish")
    public Result getPublish() {
        return Result.success(documentService.getPublish());
    }

    @GetMapping("/getUnProcessed")
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public Result getUnProcessed() {
        return Result.success(documentService.getUnProcessed());
    }
    @GetMapping("/getMyDocument")
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public Result getMyDocument() {
        return Result.success(documentService.getMyDocument());
    }

    @GetMapping("/getProcessed")
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public Result getProcessed() {
        return Result.success(documentService.getProcessed());
    }
}
