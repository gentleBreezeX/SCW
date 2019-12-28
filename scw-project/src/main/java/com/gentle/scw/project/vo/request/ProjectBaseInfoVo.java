package com.gentle.scw.project.vo.request;

import java.util.List;

import com.gentle.scw.common.vo.BaseVo;
import com.gentle.scw.project.bean.TProjectInitiator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ProjectBaseInfoVo extends BaseVo {
	@ApiModelProperty("项目之前的临时token")
	private String projectToken;// 项目的临时token
	@ApiModelProperty("项目的分类id")
	private List<Integer> typeids; // 项目的分类id
	@ApiModelProperty("项目的标签id")
	private List<Integer> tagids; // 项目的标签id
	@ApiModelProperty("项目名称")
	private String name;// 项目名称
	@ApiModelProperty("项目简介")
	private String remark;// 项目简介
	@ApiModelProperty("筹资金额")
	private Integer money;// 筹资金额
	@ApiModelProperty("筹资天数")
	private Integer day;// 筹资天数
	@ApiModelProperty("项目头部图片")
	private String headerImage;// 项目头部图片
	@ApiModelProperty("项目详情图片")
	private List<String> detailsImage;// 项目详情图片
	@ApiModelProperty("发起人的信息")
	private TProjectInitiator projectInitiator;
}
