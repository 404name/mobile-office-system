package com.curity.office.handler;

import com.curity.office.common.HttpCode;
import com.curity.office.common.Result;
import com.curity.office.common.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
    //Result是返回数据工具类
	public Result baseError(Exception e){
		e.printStackTrace();
		return Result.errorMsg(HttpCode.INTERNAL_SERVER_ERROR,e.getMessage());
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	//Result是返回数据工具类
	public Result ServiceException(ServiceException e){
		e.printStackTrace();
		System.out.println(e);
		return Result.build(e.getHttpCode());
	}
}
