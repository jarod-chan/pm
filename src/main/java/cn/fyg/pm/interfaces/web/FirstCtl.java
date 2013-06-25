package cn.fyg.pm.interfaces.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.service.ContractService;
import cn.fyg.pm.application.service.PjmemberService;
import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.application.service.SpmemberService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.supplier.Supplier;
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
	@Autowired
	SpmemberService spmemberService;
	
	@RequestMapping(value="first",method=RequestMethod.GET)
	public String toFirst(){
		User user=sessionUtil.getValue("user");
		//TODO 承包人账户处理
		if(isSupplierUser(user)){
			return "redirect:/first/contractor";
		}
		List<Project> projectList=pjmemberService.findUserProject(user);
		return "redirect:/first/project/"+projectList.get(0).getId()+"?target=first/home";
	}
	


	//判断用户是否承包人
	private boolean isSupplierUser(User user) {
		Spmember spmember=spmemberService.findByUser(user);
		if(spmember==null)
			return false;
		return true;
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
	
	@RequestMapping(value="first/contractor",method=RequestMethod.GET)
	public String toContractor(Map<String,Object> map){
		User user=sessionUtil.getValue("user");
		Spmember spmember=spmemberService.findByUser(user);
		Supplier supplier = spmember.getSupplier();
		sessionUtil.setValue("supplier", supplier);
		List<Contract> supplierContract = contractService.findBySupplier(supplier);
		List<Project> projectList=getContractProject(supplierContract);
		map.put("projectList", projectList);
		return "first/contractor";
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
