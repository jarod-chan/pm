package cn.fyg.pm.interfaces.web.module.system.frame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractSpecs;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("fm")
public class FrameCtl {
	
	private static final String PATH = "system/frame/";
	private interface Page {
		String TASK = PATH + "task";
		String PROJECT = PATH + "project";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	PjmemberService pjmemberService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ContractService contractService;
	
	@RequestMapping(value="task",method=RequestMethod.GET)
	public String toTask(){
		return Page.TASK;
	}
	
	@RequestMapping(value="project",method=RequestMethod.GET)
	public String toProject(Map<String,Object> map){
		List<Project> projectList=getProjectList();
		map.put("projectList", projectList);
		
	    Project project = sessionUtil.getValue("project");
		map.put("project", project);
		
		
		Map<User, Role> userRole = pjmemberService.getProjectUserRole(project);
		map.put("userRole", userRole);
		
		return Page.PROJECT;
	}

	public List<Project> getProjectList() {
		Supplier supplier=sessionUtil.getValue("supplier");
		if(supplier==null){			
			User user=sessionUtil.getValue("user");
			return this.pjmemberService.getUserProject(user);
		}else{
			Specifications<Contract> spec=Specifications.where(ContractSpecs.withSupplier(supplier));
			List<Contract> supplierContract = this.contractService.findAll(spec, new Sort(new Order(Direction.ASC,"id")));
			return getContractProject(supplierContract);
		}
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
	
	@RequestMapping(value="changeProject",method=RequestMethod.POST)
	public String changeProject(@RequestParam("projectId")Long projectId){
		Project project=this.projectService.find(projectId);
		sessionUtil.setValue("project", project);
		return "redirect:project";
	}
	

}
