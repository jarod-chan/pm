package cn.fyg.pm.interfaces.web.module.contractor.constructcert;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.service.ConstructCertService;
import cn.fyg.pm.application.service.ConstructContService;
import cn.fyg.pm.application.service.OpinionService;
import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.domain.model.construct.constructcert.CertItemOpinion;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.module.constructcert.ConstructCertAssembler;
import cn.fyg.pm.interfaces.web.module.constructcert.ConstructCertDto;
import cn.fyg.pm.interfaces.web.module.constructcert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("contractor/{projectId}/constructcert")
public class SpConstructcertCtl {
	
	private static final String PATH = "contractor/constructcert/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	ConstructCertAssembler constructCertAssembler;
	@Autowired
	ConstructContService constructContService;
	@Autowired
	ProjectService projectService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	TaskService taskService;
	

	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		final Supplier supplier=sessionUtil.getValue("supplier");
		final Project project=new Project();
		project.setId(projectId);
		
		QuerySpec<ConstructCert> query=new QuerySpec<ConstructCert>(){
			@Override
			public List<Predicate> criterias(CriteriaBuilder builder, Root<ConstructCert> from) {
				List<Predicate> criterias=new ArrayList<Predicate>();
				criterias.add(builder.equal(from.get("constructKey").get("project"), project));
				criterias.add(builder.equal(from.get("constructKey").get("supplier"), supplier));
				return criterias;
			}

			@Override
			public List<Order> orders(CriteriaBuilder builder, Root<ConstructCert> from) {
				List<Order> orders=new ArrayList<Order>();
				orders.add(builder.desc(from.<Object>get("createdate")));
				return orders;
			}
		};
		List<ConstructCert> constructCertList = constructCertService.query(query);
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		return Page.LIST;
	}
	
	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		Project project = projectService.find(projectId);
		User user = sessionUtil.getValue("user");
		Supplier supplier=sessionUtil.getValue("supplier");
		 
		ConstructCert constructCert =constructCertId.longValue()>0?constructCertService.find(constructCertId):constructCertService.create(user,project,ConstructCertState.new_,false) ;
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findByProjectAndSupplier(project, supplier);
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("certItemOpinionList", CertItemOpinion.values());
		attechTempFile(constructCert);
		return Page.EDIT;
	}
	
	/**
	 * 添加临时附件
	 * @param constructCert
	 */
	private void attechTempFile(ConstructCert constructCert) {
		//临时附件
		if(constructCert.getConstructCertItems()!=null&&!constructCert.getConstructCertItems().isEmpty()){
			int i=0;
			for (ConstructCertItem constructCertItem : constructCert.getConstructCertItems()) {
				if(i%2==0) constructCertItem.setImgPath("imgpath.jpg");
				i++;
			}
		}
	}

	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long constructCertId,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		Project project = projectService.find(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCert constructCert = constructCertId!=null?constructCertService.find(constructCertId):constructCertService.create(user, project,ConstructCertState.saved,true);
		
		Map<Long,ConstructCertItem> constructCertMap=getConstructCertMap(constructCert.getConstructCertItems());
		
		List<ConstructCertItem> constructCertItemList = new ArrayList<ConstructCertItem>();
		for(int i=0,len=constructCertItemsId==null?0:constructCertItemsId.length;i<len;i++){
			ConstructCertItem constructCertItem=(constructCertItemsId[i]>0?constructCertMap.get(constructCertItemsId[i]):new ConstructCertItem());
			constructCertItemList.add(constructCertItem);
		}
		constructCert.setConstructCertItems(constructCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCert);
		binder.bind(request);
		constructCert=constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCert.setState(ConstructCertState.commit);
			constructCert=constructCertService.save(constructCert);
			commit(constructCert, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}
	
	private Map<Long, ConstructCertItem> getConstructCertMap(List<ConstructCertItem> constructCertItemList) {
		HashMap<Long, ConstructCertItem> constructCertMap = new HashMap<Long,ConstructCertItem>();
		for (ConstructCertItem constructCertItem : constructCertItemList) {
			constructCertMap.put(constructCertItem.getId(), constructCertItem);
		}
		return constructCertMap;
	}
	
	private void commit(ConstructCert constructCert, User user) {
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCert.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(CertVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructCertId") Long constructCertId){
		constructCertService.delete(constructCertId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructCertId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId")Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("constructCert", constructCert);
		attechTempFile(constructCert);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructCertId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findByProject(constructCert.getConstructKey().getProject());
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinionList", opinionList);
		map.put("certItemOpinionList", CertItemOpinion.values());
		attechTempFile(constructCert);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
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
		constructCert=constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			try{
				identityService.setAuthenticatedUserId(user.getKey());
				taskService.complete(taskId);
			} finally {
				identityService.setAuthenticatedUserId(null);
			}
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:/task/list";
		}
		
		return "";

	}
}
