package com.gentle.scw.webui.vo.response;

import java.io.Serializable;

import com.gentle.scw.common.vo.BaseVo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderConfirmVo implements Serializable {
	private String accessToken;
	private Integer returnid;
	private String ordernum;
	private String createdate;
	private Integer money;
	private Integer rtncount;
	private Integer status;
	private String address;
	private Integer invoice;
	private String invoicetitle;
	private String remark;
	
	private Integer memberid;
	private Integer projectid;
}
