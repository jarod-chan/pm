package cn.fyg.pm.interfaces.web.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.user.User;

@Controller
@RequestMapping("project")
public class ProjectCtl {
	
	private static final String PATH = "project/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<Project> projectList = projectService.findAll();
		map.put("projectList", projectList);
		return Page.LIST;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(Project project){
		projectService.save(project);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String save(@RequestParam("projectId") Long projectId){
		projectService.delete(projectId);
		return "redirect:list";
	}
}
