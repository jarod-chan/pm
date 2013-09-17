package cn.fyg.pm.interfaces.web.module.system.frame.company;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("fm/company")
public class CompanyCtl {
	
	private static final String PATH = "system/frame/company/";
	private interface Page {
		String PROJECT = PATH + "project";
		String TASK = PATH + "task";
		String BASE = PATH + "base";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value="task",method=RequestMethod.GET)
	public String toTask(){
		return Page.TASK;
	}
	
	@RequestMapping(value="base",method=RequestMethod.GET)
	public String toBase(){
		return Page.BASE;
	}
	
	@RequestMapping(value="project",method=RequestMethod.GET)
	public String toProject(Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		List<Project> projectList=this.pjmemberService.getUserProject(user);
		map.put("projectList", projectList);
		
	    Project project = sessionUtil.getValue("project");
		map.put("project", project);
		
		
		Map<User, Role> userRole = pjmemberService.getProjectUserRole(project);
		map.put("userRole", userRole);
		
		return Page.PROJECT;
	}


	
	@RequestMapping(value="changeProject",method=RequestMethod.POST)
	public String changeProject(@RequestParam("projectId")Long projectId){
		Project project=this.projectService.find(projectId);
		sessionUtil.setValue("project", project);
		return "redirect:main";
	}
	

}
