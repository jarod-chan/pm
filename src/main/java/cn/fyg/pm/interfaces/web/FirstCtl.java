package cn.fyg.pm.interfaces.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.contract.Contract;
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
		return "redirect:/first/project/"+projectList.get(0).getId();
	}
	
	@RequestMapping(value="first/project/{projectId}",method=RequestMethod.GET)
	public String toProject(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		List<Project> projectList=pjmemberService.findUserProject(user);
		map.put("projectList", projectList);
		Project project=projectService.find(projectId);
		map.put("project", project);
		sessionUtil.setValue("project", project);
		List<Contract> contractList = contractService.findByProject(project);
		map.put("contractList", contractList);
		return "project";
	}

}
