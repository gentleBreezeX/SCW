package com.gentle.scw.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gentle.scw.common.utils.AppDateUtils;
import com.gentle.scw.project.bean.TProject;
import com.gentle.scw.project.bean.TProjectImages;
import com.gentle.scw.project.bean.TProjectImagesExample;
import com.gentle.scw.project.bean.TProjectInitiator;
import com.gentle.scw.project.bean.TProjectInitiatorExample;
import com.gentle.scw.project.bean.TProjectTag;
import com.gentle.scw.project.bean.TProjectType;
import com.gentle.scw.project.bean.TReturn;
import com.gentle.scw.project.bean.TReturnExample;
import com.gentle.scw.project.mapper.TProjectImagesMapper;
import com.gentle.scw.project.mapper.TProjectInitiatorMapper;
import com.gentle.scw.project.mapper.TProjectMapper;
import com.gentle.scw.project.mapper.TProjectTagMapper;
import com.gentle.scw.project.mapper.TProjectTypeMapper;
import com.gentle.scw.project.mapper.TReturnMapper;
import com.gentle.scw.project.service.ProjectService;
import com.gentle.scw.project.vo.request.ProjectRedisStorageVo;
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private TProjectMapper projectMapper;
	@Autowired
	private TProjectImagesMapper projectImagesMapper;
	@Autowired
	private TProjectTagMapper projectTagMapper;
	@Autowired
	private TProjectTypeMapper projectTypeMapper;
	@Autowired
	private TProjectInitiatorMapper projectInitiatorMapper;
	@Autowired
	private TReturnMapper returnMapper;
	
	
	
	@Override
	public void saveProject(ProjectRedisStorageVo bigVo) {
		//1.先保存project并取得project的id t_project
		//bigVo的属性名和javaBean的属性名都一致
		TProject tProject = new TProject();
		BeanUtils.copyProperties(bigVo, tProject);
		//手动设置其他需要的值
		tProject.setMoney((long)bigVo.getMoney());
		tProject.setStatus("0");
		tProject.setFollower(0);
		tProject.setCreatedate(AppDateUtils.getFormatTime());
		projectMapper.insertSelective(tProject);
		
		//获取存入的TProject对象的id
		Integer projectId = tProject.getId();
		//2.存储图片地址t_project_images
		//创建保存图片的集合,批量插入
		List<TProjectImages> imgs = new ArrayList<TProjectImages>();
		TProjectImages headerImg = new TProjectImages();
		//保存头图
		headerImg.setProjectid(projectId);
		headerImg.setImgtype((byte) 0);
		headerImg.setImgurl(bigVo.getHeaderImage());
		imgs.add(headerImg);
		//保存详情图
		for (String imgUrl : bigVo.getDetailsImage()) {
			TProjectImages detailsImg = new TProjectImages();
			detailsImg.setProjectid(projectId);
			detailsImg.setImgtype((byte) 1);
			detailsImg.setImgurl(imgUrl);
			imgs.add(detailsImg);
		}
		//将图片集合存到数据库中
		projectImagesMapper.insertImgs(imgs);
		
		//3，存项目tags信息：t_project_tag
		//将项目的tag信息转存为TProjectTag对象集合
		List<Integer> tagids = bigVo.getTagids();
		for (Integer tagid : tagids) {
			TProjectTag tProjectTag = new TProjectTag();
			tProjectTag.setProjectid(projectId);
			tProjectTag.setTagid(tagid);
			projectTagMapper.insertSelective(tProjectTag);
		}
		
		//4.存储项目type： t_project_type
		List<Integer> typeids = bigVo.getTypeids();
		for (Integer typeid : typeids) {
			TProjectType tProjectType = new TProjectType();
			tProjectType.setProjectid(projectId);
			tProjectType.setTypeid(typeid);
			projectTypeMapper.insertSelective(tProjectType);
		}
		
		//5.存储项目发起人信息：t_project_initiator
		TProjectInitiator projectInitiator = bigVo.getProjectInitiator();
		//一定要将项目发起人所在的项目id设置给发起人
		projectInitiator.setProjectid(projectId);
		projectInitiatorMapper.insertSelective(projectInitiator);
		
		//6.存储项目回报：t_return
		List<TReturn> projectReturns = bigVo.getProjectReturns();
		for (TReturn tReturn : projectReturns) {
			tReturn.setProjectid(projectId);
			returnMapper.insertSelective(tReturn);
		}
		
	}



	@Override
	public List<TProject> getAllProjects() {
		return projectMapper.selectByExample(null);
	}



	@Override
	public List<TProjectImages> getProjectImages(Integer id) {
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		return projectImagesMapper.selectByExample(example);
	}



	@Override
	public TProject getProjectInfo(Integer projectId) {
		return projectMapper.selectByPrimaryKey(projectId);
	}



	@Override
	public List<TReturn> getProjectReturns(Integer id) {
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(id);
		return returnMapper.selectByExample(example);
	}



	@Override
	public TReturn getReturnById(Integer returnId) {
		return returnMapper.selectByPrimaryKey(returnId);
	}



	@Override
	public TProjectInitiator getprojectInitiatorByPId(Integer id) {
		TProjectInitiatorExample example = new TProjectInitiatorExample();
		example.createCriteria().andProjectidEqualTo(id);
		return projectInitiatorMapper.selectByExample(example).get(0);
	}

}
