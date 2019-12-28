package com.gentle.scw.webui.vo.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class ProjectVo implements Serializable {
	private Integer id;
	private String name;
	private String remark;
	private Long money;
	private Integer day;
	private Byte status;
	private String deploydate;
	private Long supportmoney;
	private Integer supporter;
	private Integer completion;
	private Integer memberid;
	private String createdate;
	private Integer follower;
	private String headerImage;
	private List<String> detailsImage = new ArrayList<>();
	// 项目的所有支持档位
	private List<TReturn> returns = new ArrayList<>();
}
