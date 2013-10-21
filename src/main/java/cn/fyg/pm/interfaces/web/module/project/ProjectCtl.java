package cn.fyg.pm.interfaces.web.module.project;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.RoleService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.role.RoleType;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.module.project.query.ProjectQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;

@Controller
@RequestMapping("project")
public class ProjectCtl {
	
	private static final String PATH = "project/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String PJMEMBER= PATH +"pjmember";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	PjmemberFacade pjmemberFacade;
	@Autowired
	RoleService roleService;
	
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(ProjectQuery query,Map<String,Object> map){
		List<Project> projectList = projectService.query(query);
		map.put("projectList", projectList);
		map.put("query", query);
		return Page.LIST;
	}
	
	
	@RequestMapping(value="{projectId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId") Long projectId,Map<String,Object> map){
		Project project = projectId.longValue()>0?projectService.find(projectId):projectService.create();
		map.put("project", project);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("id")Long projectId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = projectId!=null?projectService.find(projectId):projectService.create();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(project);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		projectService.save(project);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("projectId") Long projectId,RedirectAttributes redirectAttributes){
		try {
			projectService.delete(projectId);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("操作完成"));
		} catch (NoNotLastException e) {
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info(e.getMessage()));
		}
		return "redirect:list";
	}
	
	@RequestMapping(value="{projectId}/pjmember",method=RequestMethod.GET)
	public String toPjmember(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project= this.projectService.find(projectId);
		List<User> userList = this.userService.findAll();
		List<Role> pjroles = this.roleService.findByRoleType(RoleType.project);
		Map<User, Role> projectUserRole = pjmemberService.getProjectUserRole(project);
		List<PjmemberDto> pjmemberDtos =PjmemberAssembler.build(userList, projectUserRole);

		map.put("project", project);
		map.put("pjmemberDtos", pjmemberDtos);
		map.put("pjroles", pjroles);
		return Page.PJMEMBER;
	}

	@RequestMapping(value="{projectId}/pjmember/save",method=RequestMethod.POST)
	public String savePjmember(@PathVariable("projectId")Long projectId,PjmemberPage pjmemberPage){
		Project project=projectService.find(projectId);
		pjmemberFacade.savePjmember(project, pjmemberPage.getPlt());
		return "redirect:/project/list";
	}
}
