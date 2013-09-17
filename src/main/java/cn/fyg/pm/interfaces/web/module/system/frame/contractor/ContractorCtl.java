package cn.fyg.pm.interfaces.web.module.system.frame.contractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SpmemberService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("fm/contractor")
public class ContractorCtl {
	
	private static final String PATH = "system/frame/contractor/";
	private interface Page {
		String PROJECT = PATH + "project";
		String TASK = PATH +"task";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ContractService contractService;
	@Autowired
	SpmemberService spmemberService;
	
	@RequestMapping(value="task",method={RequestMethod.GET,RequestMethod.POST})
	public String toTask(@RequestParam(value="projectId",required=false)Long projectId){
		if(projectId!=null){
			Project project=this.projectService.find(projectId);
			sessionUtil.setValue("project", project);
		}
		return Page.TASK;
	}
	
	@RequestMapping(value="project",method=RequestMethod.GET)
	public String toProject(Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		Supplier supplier=spmemberService.getUserSupplier(user);
		List<Contract> supplierContract = contractService.findBySupplier(supplier);
		List<Project> projectList=getContractProject(supplierContract);
		map.put("projectList", projectList);
		Project project = sessionUtil.getValue("project");
		map.put("project", project);
		
		return Page.PROJECT;
	}

	private List<Project> getContractProject(List<Contract> supplierContract) {
		List<Project> projectList= new ArrayList<Project>();
		Set<Long> projectIdSet=new HashSet<Long>();
		for (Contract contract : supplierContract) {
			Project project = contract.getProject();
			Long projectId=project.getId();
			if(!projectIdSet.contains(projectId)){
				projectList.add(project);
				projectIdSet.add(projectId);
			}
		}
		return projectList;
	}
	
	
	
}
