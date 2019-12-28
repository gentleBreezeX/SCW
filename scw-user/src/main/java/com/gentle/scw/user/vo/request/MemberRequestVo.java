package com.gentle.scw.user.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class MemberRequestVo {
	@ApiModelProperty("手机号码")
	private String loginacct;//手机号码
	@ApiModelProperty("密码")
	private String userpswd;//密码
	@ApiModelProperty("验证码")
	private String code;//验证码
	@ApiModelProperty("邮箱")
	private String email;//邮箱
	@ApiModelProperty("用户类型 0-个人 1-企业")
	private String usertype;//用户类型 0-个人 1-企业
	
}
