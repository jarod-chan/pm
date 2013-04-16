package cn.fyg.pm.interfaces.web.module;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.constructcont.ConstructContState;
import cn.fyg.pm.domain.contract.Contract;
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
		List<Contract> contractList = contractService.findAll();
		map.put("contractList", contractList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCont constructCont){
		User user = sessionUtil.getValue("user");
/*		Contract contract = contractService.find(constructCont.getContract().getId());
		constructCont.setProject(contract.getProject());*/
		constructCont.setState(ConstructContState.saved);
		constructCont.setCreater(user);
		constructCont.setCreateTime(new Date());
		constructContService.save(constructCont);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructContId") Long constructContId){
		constructContService.delete(constructContId);
		return "redirect:list";
	}

	
}