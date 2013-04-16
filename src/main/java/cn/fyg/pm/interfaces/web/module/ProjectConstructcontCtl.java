package cn.fyg.pm.interfaces.web.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.constructcont.ConstructCont;

@Controller
public class ProjectConstructcontCtl {
	
	private static final String PATH = "projectconstructcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	ConstructContService constructContService;
	
	@RequestMapping(value="project/{projectId}/constructcont/list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCont> constructContList = constructContService.findAll();
		map.put("constructContList", constructContList);
		return Page.LIST;
	}

}
