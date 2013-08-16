package cn.fyg.pm.interfaces.web.module.trace.designcont;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.module.trace.designcont.query.ContQuery;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/designcont")
public class DesignContCtl {
	
	private static final String PATH="trace/designcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	DesignContService designContService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	DesignNotiService designNotiService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,ContQuery query,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		query.setProject(project);
		List<DesignCont>  designContList= this.designContService.query(query);
		map.put("designContList", designContList);
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{designContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		DesignCont designCont = designContId.longValue()>0?this.designContService.find(designContId):this.designContService.create(user,project,DesignContState.new_);
		map.put("designCont", designCont);
		List<DesignNoti> designNotiList = this.designNotiService.findByProject(project,DesignNotiState.saved);
		map.put("designNotiList", designNotiList);
//		DesignNoti designNoti = this.designNotiService.findByDesignKey(designCont.getDesignKey());
//		map.put("designNoti", designNoti);
		return Page.EDIT;
	}

}
