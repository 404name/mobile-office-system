package com.curity.office.model;/*
 * Copyright © 2020-2035 erupt.xyz All rights reserved.
 * Author: YuePeng (erupts@126.com)
 */

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import xyz.erupt.annotation.*;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.handler.DictCodeChoiceFetchHandler;
import xyz.erupt.jpa.model.BaseModel;
import java.util.Set;
import java.util.Date;

@Erupt(name = "Document")
@Table(name = "office_document")
@Entity
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//TODO: CNM的序列化
public class Document extends BaseModel {

        @EruptField(
                views = @View(
                        title = "发文编号"
                ),
                edit = @Edit(
                        title = "发文编号",
                        type = EditType.INPUT, search = @Search, notNull = true,
                        inputType = @InputType
                )
        )
        private String code;

        @EruptField(
                views = @View(
                        title = "发文标题"
                ),
                edit = @Edit(
                        title = "发文标题",
                        type = EditType.INPUT, search = @Search, notNull = true,
                        inputType = @InputType
                )
        )
        private String title;

        @EruptField(
                views = @View(
                        title = "发文日期"
                ),
                edit = @Edit(
                        title = "发文日期",
                        type = EditType.DATE, search = @Search,
                        dateType = @DateType(type = DateType.Type.DATE_TIME)
                )
        )
        private Date date;

        @EruptField(
                views = @View(
                        title = "等级"
                ),
                edit = @Edit(
                title = "等级",
                        search = @Search,
                type = EditType.CHOICE,
                choiceType = @ChoiceType(
                        fetchHandler = DictCodeChoiceFetchHandler.class,
                        //参数一必填，需替换成实际的字典编码
                        //参数二可不填，表示缓存时间，默认为3000毫秒
                        fetchHandlerParams = {"estate", "5000"}
                ))
        )
        private String estate;

        @Lob
        @EruptField(
                views = @View(title = "内容(UEditor)", type = ViewType.HTML, export = false),
                edit = @Edit(title = "内容(UEditor)", type = EditType.HTML_EDITOR, notNull = true)
        )
        private String content;

        @EruptField(
                edit = @Edit(
                        title = "发布者id",
                        type = EditType.INPUT, notNull = true,
                        inputType = @InputType
                )
        )
        private Long publisher;
        @EruptField(
                views = @View(
                        title = "发布者"
                ),
                edit = @Edit(
                        title = "发布者",
                        type = EditType.INPUT, search = @Search, notNull = true,
                        inputType = @InputType
                )
        )
        private String publisherName;
        @EruptField(
                views = @View(
                        title = "状态"
                ),
                edit = @Edit(
                title = "状态",
                        search = @Search,
                type = EditType.CHOICE,
                choiceType = @ChoiceType(
                        fetchHandler = DictCodeChoiceFetchHandler.class,
                        //参数一必填，需替换成实际的字典编码
                        //参数二可不填，表示缓存时间，默认为3000毫秒
                        fetchHandlerParams = {"state", "5000"}
                ))
        )
        private String state;

        @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
        @JoinColumn(name = "document_id")
        @EruptField(
                edit = @Edit(title = "历史记录", type = EditType.TAB_TABLE_ADD)
        )
        private Set<DocumentHistory> DocumentHistory;
}
