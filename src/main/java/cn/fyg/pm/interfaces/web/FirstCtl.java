package cn.fyg.pm.interfaces.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.service.ContractService;
import cn.fyg.pm.application.service.PjmemberService;
import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
public class FirstCtl {
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ContractService contractService;
	
	@RequestMapping(value="first",method=RequestMethod.GET)
	public String toFirst(){
		User user=sessionUtil.getValue("user");
		List<Project> projectList=pjmemberService.findUserProject(user);
		return "redirect:/first/project/"+projectList.get(0).getId()+"?target=first/home";
	}
	
	@RequestMapping(value="first/project/{projectId}",method=RequestMethod.GET)
	public String toProject(@PathVariable("projectId")Long projectId,@RequestParam("target")String target,Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		List<Project> projectList=pjmemberService.findUserProject(user);
		map.put("projectList", projectList);
		Project project=projectService.find(projectId);
		map.put("project", project);
		sessionUtil.setValue("project", project);
		List<Contract> contractList = contractService.findByProject(project);
		map.put("contractList", contractList);
		List<Pjmember> pjmemberList = pjmemberService.findByProject(project);
		map.put("pjmemberList", pjmemberList);
		
		map.put("target", target);
		return "first/first";
	}
	
	@RequestMapping(value="first/home",method=RequestMethod.GET)
	public String toFirstHome(){
		return "first/home";
	}

	@RequestMapping(value="first/notfinish",method=RequestMethod.GET)
	public String toNotfinish(){
		return "first/notfinish";
	}
}
