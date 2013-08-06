package cn.fyg.pm.interfaces.web.module.contractor.projectinfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

@Controller
@RequestMapping("{projectId}/contractor/{supplierId}/projectinfo")
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
	public String toProjectInfo(@PathVariable("supplierId")Long supplierId,@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project=projectService.find(projectId);
		map.put("project", project);
		List<User> projectUsers = pjmemberService.getProjectUser(project);
		map.put("projectUsers", projectUsers);
		return Page.PROJECTINFO;
	}

}
