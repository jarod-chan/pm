package cn.fyg.pm.interfaces.web.module;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.constructcert.ConstructCert;
import cn.fyg.pm.domain.constructcert.ConstructCertState;
import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.module.shared.session.SessionUtil;

@Controller
@RequestMapping("constructcert")
public class ConstructCertCtl {
	
	private static final String PATH="constructcert/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	ConstructContService constructContService;
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCert> constructCertList = constructCertService.findAll();
		map.put("constructCertList", constructCertList);
		return Page.LIST;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		List<ConstructCont> constructContList = constructContService.findAll();
		map.put("constructContList", constructContList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCert constructCert){
		User user = sessionUtil.getValue("user");
		ConstructCont constructCont = constructContService.find(constructCert.getConstructCont().getId());
		constructCert.setProject(constructCont.getProject());
		constructCert.setContract(constructCont.getContract());
		constructCert.setState(ConstructCertState.saved);
		constructCert.setCreater(user);
		constructCert.setCreateTime(new Date());
		constructCertService.save(constructCert);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructCertId") Long constructCertId){
		constructCertService.delete(constructCertId);
		return "redirect:list";
	}

}
