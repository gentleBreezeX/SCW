package com.gentle.scw.user.vo.response;

import java.io.Serializable;

import com.gentle.scw.common.vo.BaseVo;

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
public class MemberResponseVo extends BaseVo implements Serializable {
	@ApiModelProperty("手机号码")
	private String loginacct;//手机号码
	@ApiModelProperty("用户姓名")
	private String username;
	@ApiModelProperty("用户访问控制令牌")
	private String accessToken;
	@ApiModelProperty("邮箱")
	private String email;//邮箱
	@ApiModelProperty("用户类型 0-个人 1-企业")
	private String usertype;//用户类型 0-个人 1-企业
}
