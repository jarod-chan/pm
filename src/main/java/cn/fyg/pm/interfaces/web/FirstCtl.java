package cn.fyg.pm.interfaces.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SpmemberService;
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
	
	@RequestMapping(value="first/home",method=RequestMethod.GET)
	public String toFirstHome(){
		return "first/home";
	}

	@RequestMapping(value="first/notfinish",method=RequestMethod.GET)
	public String toNotfinish(){
		return "first/notfinish";
	}
	
}
