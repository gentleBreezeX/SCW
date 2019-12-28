package com.gentle.scw.project.vo.request;

import com.gentle.scw.common.vo.BaseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectLegalPropleInfoVo extends BaseVo {
	
	@ApiModelProperty("projectToken")
	private String projectToken;
	@ApiModelProperty("支付宝收款账号")
	private String alipayAccount;
	@ApiModelProperty("身份证号码")
	private String idcard;
	
}
