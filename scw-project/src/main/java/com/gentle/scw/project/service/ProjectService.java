package com.gentle.scw.project.service;

import java.util.List;

import com.gentle.scw.project.bean.TProject;
import com.gentle.scw.project.bean.TProjectImages;
import com.gentle.scw.project.bean.TProjectInitiator;
import com.gentle.scw.project.bean.TReturn;
import com.gentle.scw.project.vo.request.ProjectRedisStorageVo;

public interface ProjectService {

	void saveProject(ProjectRedisStorageVo bigVo);

	List<TProject> getAllProjects();

	List<TProjectImages> getProjectImages(Integer id);

	TProject getProjectInfo(Integer projectId);

	List<TReturn> getProjectReturns(Integer id);

	TReturn getReturnById(Integer returnId);

	TProjectInitiator getprojectInitiatorByPId(Integer id);

}
