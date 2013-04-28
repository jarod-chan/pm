package cn.fyg.pm.interfaces.web.module.consturctcert;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("constructcert")
public class ConstructCertCtl {
	
	private static final String PATH="constructcert/";
	private interface Page {
		String LIST = PATH + "list";
		String NEW = PATH + "new";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	ConstructContService constructContService;
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	ConstructCertAssembler constructCertAssembler;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCert> constructCertList = constructCertService.findAll();
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		return Page.LIST;
	}
	
	@RequestMapping(value="new",method=RequestMethod.GET)
	public String toNew(Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		Project project = sessionUtil.getValue("project");
		map.put("project", project);
		List<ConstructCont> constructContList = constructContService.findByProject(project);
		map.put("constructContList", constructContList);
		ConstructCert constructCert = constructCertService.create(user);
		map.put("constructCert", constructCert);
		return Page.NEW;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCert constructCert){
		User user = sessionUtil.getValue("user");
		constructCert.setState(ConstructCertState.saved);
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCertService.save(constructCert);
		return "redirect:list";
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long id,@RequestParam("constructCertItemsId") Long[] constructCertItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		ConstructCert constructCert = constructCertService.find(id);
		Map<Long,ConstructCertItem> constructCertMap=getConstructCertMap(constructCert.getConstructCertItems());
		
		List<ConstructCertItem> constructCertItemList = new ArrayList<ConstructCertItem>();
		for(int i=0,len=constructCertItemsId.length;i<len;i++){
			ConstructCertItem constructCertItem=(constructCertItemsId[i]>0?constructCertMap.get(constructCertItemsId[i]):new ConstructCertItem());
			constructCertItemList.add(constructCertItem);
		}
		constructCert.setConstructCertItems(constructCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCert);
		binder.bind(request);
		constructCertService.save(constructCert);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
		
		return "redirect:list";
	}
	

	private Map<Long, ConstructCertItem> getConstructCertMap(List<ConstructCertItem> constructCertItemList) {
		HashMap<Long, ConstructCertItem> constructCertMap = new HashMap<Long,ConstructCertItem>();
		for (ConstructCertItem constructCertItem : constructCertItemList) {
			constructCertMap.put(constructCertItem.getId(), constructCertItem);
		}
		return constructCertMap;
	}

	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findByProject(constructCert.getConstructKey().getProject());
		map.put("constructContList", constructContList);
		return Page.EDIT;
	}
	
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructCertId") Long constructCertId){
		constructCertService.delete(constructCertId);
		return "redirect:list";
	}

}
