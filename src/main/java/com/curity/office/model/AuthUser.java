package com.curity.office.model;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.handler.DictCodeChoiceFetchHandler;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: erupt-example
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2022-02-08 17:14
 **/
@Erupt(name = "Document")
@Table(name = "office_auth_user")
@Entity
@Data
public class AuthUser extends BaseModel {
    @EruptField(
            views = @View(title = "用户头像"),
            edit = @Edit(title = "用户头像", type = EditType.ATTACHMENT,
                    attachmentType = @AttachmentType(type = AttachmentType.Type.IMAGE, maxLimit = 3))
    )
    private String pic;

    @EruptField(
            edit = @Edit(
                    title = "用户绑定id",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private Long userId;
    @EruptField(
            views = @View(title = "第三方授权码"),
            edit = @Edit(title = "第三方授权码", notNull = true)
    )
    private String authCode;
    @EruptField(
            views = @View(
                    title = "用户姓名"
            ),
            edit = @Edit(
                    title = "用户姓名",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String userName;

    @EruptField(
            views = @View(title = "审批状态"),
            edit = @Edit(title = "审批状态", notNull = true, boolType = @BoolType(trueText = "通过", falseText = "退回"), search = @Search)
    )
    private Boolean publish;




    @EruptField(
            views = @View(
                    title = "最近一次登录",
                    sortable = true
            ),
            edit = @Edit(
                    title = "最近一次登录",
                    type = EditType.DATE, search = @Search,
                    dateType = @DateType(type = DateType.Type.DATE_TIME)
            )
    )
    private Date date;

    @EruptField(
            views = @View(
                    title = "最近一次登录"
            ),
            edit = @Edit(
                    title = "状态",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = DictCodeChoiceFetchHandler.class,
                            //参数一必填，需替换成实际的字典编码
                            //参数二可不填，表示缓存时间，默认为3000毫秒
                            fetchHandlerParams = {"state", "5000"}
                    ))
    )
    private String state;
}
