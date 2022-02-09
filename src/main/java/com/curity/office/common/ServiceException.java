package com.curity.office.common;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private Integer code;
    private HttpCode httpCode;
    /**
     * 使用已有的错误类型
     * @param type 枚举类中的错误类型
     */
    public ServiceException(HttpCode type){
        super(type.getZhMsg());
        this.code = type.getCode();
        this.httpCode = type;
    }

    /**
     * 自定义错误类型
     * @param code 自定义的错误码
     * @param msg 自定义的错误提示
     */
    public ServiceException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code=" + code +
                ", httpCode=" + httpCode +
                '}';
    }
}
