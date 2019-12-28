package com.gentle.scw.common.consts;

public interface AppConsts {
	//redis中用于拼接验证码相关信息键前缀的常量
	String CODE_PREFIX = "code:";
	//redis中验证码次数的键的拼接后缀
	String CODE_COUNT_SUFFIX = ":count";
	//redis中存储验证码的键后缀
	String CODE_SUFFIX = ":code";
	//redis存储登录用户信息的键前缀
	String MEMBER_PREFIX = "member:";
	//redis中存储用户登录信息的键后缀
	String MEMBER_SUFFIX = ":member";
	//redis中存储临时发布的众筹项目的前缀键
	String TEMP_PROJECT_PREFIX = "project:creat:temp:";
	
	//响应成功的状态码
	Integer SUCCESS_STUTAS_CODE = 10000;
	//响应失败的状态码
	Integer ERROR_STUTAS_CODE = 10001;
	
	
}
