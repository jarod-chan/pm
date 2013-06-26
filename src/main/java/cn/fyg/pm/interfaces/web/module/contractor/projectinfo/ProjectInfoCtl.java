package cn.fyg.pm.interfaces.web.module.contractor.projectinfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.service.PjmemberService;
import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.project.Project;

@Controller
@RequestMapping("contractor/{projectId}/projectinfo")
public class ProjectInfoCtl {
	
	private static final String PATH = "contractor/projectinfo/";
	private interface Page {
		String PROJECTINFO = PATH + "projectinfo";
	}
	
	@Autowired
	ProjectService projectService;
	@Autowired
	PjmemberService pjmemberService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String toProjectInfo(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project=projectService.find(projectId);
		map.put("project", project);
		List<Pjmember> pjmemberList = pjmemberService.findByProject(project);
		map.put("pjmemberList", pjmemberList);
		return Page.PROJECTINFO;
	}

}
