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
import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("fm/company")
public class CompanyCtl {
	
	private static final String PATH = "frame/company/";
	private interface Page {
		String MAIN = PATH + "main";
		String TASK = PATH +"task";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value="task",method={RequestMethod.GET,RequestMethod.POST})
	public String toTask(@RequestParam(value="menuIdx",required=false)Long menuIdx){
		if(menuIdx!=null){
			sessionUtil.setValue("menuIdx", menuIdx);
		}
		return Page.TASK;
	}
	
	@RequestMapping(value="main",method=RequestMethod.GET)
	public String toMain(Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		List<Project> projectList=this.pjmemberService.getUserProject(user);
		map.put("projectList", projectList);
		
		Project project = getSessionProject(projectList.get(0));
		map.put("project", project);
		
		Long menuIdx= getSessionMenuIdx();
		map.put("menuIdx", menuIdx);
		
		Map<User, Pjrole> userRole = pjmemberService.getProjectUserRole(project);
		map.put("userRole", userRole);
		
		return Page.MAIN;
	}

	private Long getSessionMenuIdx() {
		Long menuIdx=-1L;
		Object obj = sessionUtil.getValue("menuIdx");
		if(obj!=null){
			menuIdx=(Long)obj;
		}
		return menuIdx;
	}

	public Project getSessionProject(Project firstProject) {
		Project project=firstProject;
		Object obj = sessionUtil.getValue("project");
		if(obj==null){
			project =firstProject;
			sessionUtil.setValue("project", project);
		}else{
			project=(Project)obj;
		}
		return project;
	}
	
	@RequestMapping(value="changeProject",method=RequestMethod.POST)
	public String changeProject(@RequestParam("projectId")Long projectId,@RequestParam("menuIdx")Long menuIdx){
		Project project=this.projectService.find(projectId);
		sessionUtil.setValue("project", project);
		sessionUtil.setValue("menuIdx", menuIdx);
		return "redirect:main";
	}
	

}
