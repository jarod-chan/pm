package cn.fyg.pm.interfaces.web.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.ConstructKeyService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.contract.Contract;
import cn.fyg.pm.domain.project.Project;

@Controller
public class ConstructKeyCtl {
	
	private static final String PATH="constructkey/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	ConstructKeyService constructKeyService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ContractService contractService;
	
	@RequestMapping(value="project/{projectId}/constructkey/list",method=RequestMethod.GET)
	public String toList(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project = projectService.find(projectId);
		List<ConstructKey> constructKeyList = constructKeyService.findByProject(project);
		map.put("constructKeyList", constructKeyList);
		map.put("project", project);
		return Page.LIST;
	}
	
	@RequestMapping(value="project/{projectId}/constructkey/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project = projectService.find(projectId);
		List<Contract> contractList = contractService.findByProject(project);
		map.put("contractList", contractList);
		map.put("project", project);
		return Page.EDIT;
	}
	
	@RequestMapping(value="constructkey/save",method=RequestMethod.POST)
	public String save(ConstructKey constructKey){
		constructKeyService.save(constructKey);
		return String.format("redirect:/project/%s/constructkey/list",constructKey.getProject().getId());
	}

}
