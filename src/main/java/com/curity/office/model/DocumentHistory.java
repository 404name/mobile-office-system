package com.curity.office.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.handler.DictChoiceFetchHandler;
import xyz.erupt.upms.handler.DictCodeChoiceFetchHandler;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liyuepeng
 * @date 2021-01-02.
 */
@Erupt(name = "一对多新增")
@Table(name = "office_document_history")
@Entity
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DocumentHistory extends BaseModel {

    @EruptField(
            views = @View(title = "用户头像"),
            edit = @Edit(title = "用户头像", type = EditType.ATTACHMENT,
                    attachmentType = @AttachmentType(type = AttachmentType.Type.IMAGE, maxLimit = 3))
    )
    private String pic;

    @EruptField(
            edit = @Edit(
                    title = "审批人id",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private Long approver;

    @EruptField(
            views = @View(
                    title = "审批人"
            ),
            edit = @Edit(
                    title = "审批人",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String approverName;

    @EruptField(
            views = @View(title = "审批状态"),
            edit = @Edit(title = "审批状态", notNull = true, boolType = @BoolType(trueText = "通过", falseText = "退回"), search = @Search)
    )
    private Boolean publish;


    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", notNull = true, search = @Search(vague = true))
    )
    private String remark;

    @EruptField(
            views = @View(
                    title = "更新日期",
                    sortable = true
            ),
            edit = @Edit(
                    title = "更新日期",
                    type = EditType.DATE, search = @Search,
                    dateType = @DateType(type = DateType.Type.DATE_TIME)
            )
    )
    private Date date;

    @EruptField(
            views = @View(
                    title = "状态"
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
