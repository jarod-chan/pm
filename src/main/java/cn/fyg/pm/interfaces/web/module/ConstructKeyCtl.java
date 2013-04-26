package cn.fyg.pm.interfaces.web.module;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ConstructKeyService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.constructcert.ConstructCert;
import cn.fyg.pm.domain.constructcert.ConstructCertState;
import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.constructcont.ConstructContState;
import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.contract.Contract;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
public class ConstructKeyCtl {
	
	private static final String PATH="constructkey/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String CONT = PATH + "cont";
		String CERT = PATH + "cert";
		String CONTVIEW = PATH + "contview";
		String CERTVIEW = PATH + "certview";
	}
	
	@Autowired
	ConstructKeyService constructKeyService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ContractService contractService;
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	ConstructContService constructContService;
	@Autowired
	SessionUtil sessionUtil;
	
	
	
	@RequestMapping(value="project/{projectId}/constructkey/list",method=RequestMethod.GET)
	public String toList(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project = projectService.find(projectId);
		List<ConstructKey> constructKeyList = constructKeyService.findByProject(project);
		for(ConstructKey constructKey:constructKeyList){
			constructKey.setConstructCont(constructContService.findByConstructKey(constructKey));
			constructKey.setConstructCert(constructCertService.findByConstructKey(constructKey));
		}
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
	
	@RequestMapping(value="project/{projectId}/constructkey/delete",method=RequestMethod.POST)
	public String delete(@PathVariable("projectId")Long projectId,@RequestParam("constructKeyId") Long constructKeyId){
		ConstructKey constructKey = constructKeyService.find(constructKeyId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructKey);
		if(constructCont!=null){
			constructContService.delete(constructCont.getId());
		}
		ConstructCert constructCert = constructCertService.findByConstructKey(constructKey);
		if(constructCert!=null){
			constructCertService.delete(constructCert.getId());
		}
		constructKeyService.delete(constructKeyId);
		return String.format("redirect:/project/%s/constructkey/list",projectId);
	}
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cont/new",method=RequestMethod.GET)
	public String toContNew(@PathVariable("projectId")Long projectId,@PathVariable("constructKeyId")Long constructKeyId,Map<String,Object> map){
		ConstructKey constructKey = constructKeyService.find(constructKeyId);
		map.put("constructKey", constructKey);
		return Page.CONT;
	}
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cont/save",method=RequestMethod.POST)
	public String saveCont(@PathVariable("projectId")Long projectId,ConstructCont constructCont){
		User user = sessionUtil.getValue("user");
		constructCont.setState(ConstructContState.saved);
		constructCont.setCreater(user);
		constructCont.setCreatedate(new Date());
		constructContService.save(constructCont);
		return String.format("redirect:/project/%s/constructkey/list",projectId);
	}
	
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cont/{contId}",method=RequestMethod.GET)
	public String toContView(@PathVariable("contId")Long contId,Map<String,Object> map){
		ConstructCont constructCont = constructContService.find(contId);
		map.put("constructCont", constructCont);
		return Page.CONTVIEW;
	}
	
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cert/new",method=RequestMethod.GET)
	public String toCertNew(@PathVariable("projectId")Long projectId,@PathVariable("constructKeyId")Long constructKeyId,Map<String,Object> map){
		ConstructKey constructKey = constructKeyService.find(constructKeyId);
		map.put("constructKey", constructKey);
		return Page.CERT;
	}
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cert/save",method=RequestMethod.POST)
	public String save(@PathVariable("projectId")Long projectId,ConstructCert constructCert){
		User user = sessionUtil.getValue("user");
		constructCert.setState(ConstructCertState.saved);
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCertService.save(constructCert);
		return String.format("redirect:/project/%s/constructkey/list",projectId);
	}
	
	@RequestMapping(value="project/{projectId}/constructkey/{constructKeyId}/cert/{certid}",method=RequestMethod.GET)
	public String toCertNew(@PathVariable("certid")Long certid,Map<String,Object> map){
		ConstructCert constructCert=constructCertService.find(certid);
		map.put("constructCert", constructCert);
		return Page.CERTVIEW;
	}
}
