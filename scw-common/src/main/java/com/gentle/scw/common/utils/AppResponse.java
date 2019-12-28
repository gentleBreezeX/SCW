package com.gentle.scw.common.utils;


import java.io.Serializable;

import com.gentle.scw.common.consts.AppConsts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppResponse<T> implements Serializable{
	//响应状态码 
	private int code;//10000表示成功  10001表示xxx异常
	//响应的信息：描述本次响应状态的
	private String message;
	//响应数据
	private T data;
	
	//响应成功的方法
	public static <T> AppResponse<T> ok(T data, String message){
		return new AppResponse<T>(AppConsts.SUCCESS_STUTAS_CODE, message, data);
	}
	
	//响应失败的方法
	public static <T> AppResponse<T> fail(T data, String message){
		return new AppResponse<T>(AppConsts.ERROR_STUTAS_CODE, message, data);
	}
	
}
