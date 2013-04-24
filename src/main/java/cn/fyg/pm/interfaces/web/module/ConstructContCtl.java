package cn.fyg.pm.interfaces.web.module;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.constructcont.ConstructContState;
import cn.fyg.pm.domain.contract.Contract;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.module.shared.session.SessionUtil;

@Controller
@RequestMapping("constructcont")
public class ConstructContCtl {
	
	private static final String PATH="constructcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@Autowired
	ContractService contractService;
	@Autowired
	ConstructContService constructContService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCont> constructContList = constructContService.findAll();
		map.put("constructContList", constructContList);
		return Page.LIST;
	}

	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		map.put("project", project);
		List<Contract> contractList = contractService.findByProject(project);
		map.put("contractList", contractList);
		ConstructCont constructCont = constructContService.create(user);
		map.put("constructCont", constructCont);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCont constructCont){
		User user = sessionUtil.getValue("user");
		constructCont.setState(ConstructContState.saved);
		constructCont.setCreater(user);
		constructCont.setCreatedate(new Date());
		constructContService.save(constructCont);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructContId") Long constructContId){
		constructContService.delete(constructContId);
		return "redirect:list";
	}

	
}