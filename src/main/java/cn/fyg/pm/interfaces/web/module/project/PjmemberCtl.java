package cn.fyg.pm.interfaces.web.module.project;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.RoleService;
import cn.fyg.pm.application.facade.PjmemberFacade;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.role.RoleType;
import cn.fyg.pm.interfaces.web.module.project.pjmember.PjmemberPage;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;


@Controller
@RequestMapping("project")
public class PjmemberCtl {
	
	@Autowired
	ProjectService projectService;
	@Autowired
	PjmemberFacade pjmemberFacade;
	@Autowired
	RoleService roleService;
	
	private static final String PATH = "project/";
	private interface Page {
		String PJMEMBER= PATH +"pjmember";
	}

	
	@RequestMapping(value="{projectId}/pjmember",method=RequestMethod.GET)
	public String toPjmember(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project= this.projectService.find(projectId);
		map.put("project", project);

		List<Pjmember> pjmembers = this.pjmemberFacade.getProjecMember(project);
		map.put("pjmembers", pjmembers);

		List<Role> pjroles = this.roleService.findByRoleType(RoleType.project);
		map.put("pjroles", pjroles);
		return Page.PJMEMBER;
	}

	@RequestMapping(value="{projectId}/pjmember/save",method=RequestMethod.POST)
	public String savePjmember(@PathVariable("projectId")Long projectId,PjmemberPage pjmemberPage,RedirectAttributes redirectAttributes){
		Project project=projectService.find(projectId);
		pjmemberFacade.savePjmember(project, pjmemberPage.getPjmembers());
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:/project/list";
	}

}
