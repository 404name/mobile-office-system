package com.curity.office.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements  Serializable{
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public static Result build(HttpCode statusCode) {
        return new Result(statusCode.getCode(), statusCode.getEnMsg(), statusCode.getZhMsg());
    }
    public static Result build(HttpCode statusCode,Object obj) {
        return new Result(statusCode.getCode(), statusCode.getEnMsg(), obj);
    }

    public static Result build(Integer status, String message, Object data) {
        return new Result(status, message, data);
    }
    public static Result errorMsg(HttpCode statusCode,String message) {
        return new Result(statusCode.getCode(), message,statusCode.getZhMsg());
    }
    public static Result errorMsg(String message) {
        return new Result(500, message, null);
    }
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public Result() {
    }

    public Result(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(Object data) {
        this.status = 200;
        this.message = "success";
        this.data = data;
    }

}
