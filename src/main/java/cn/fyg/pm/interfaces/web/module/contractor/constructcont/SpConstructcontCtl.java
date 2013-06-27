package cn.fyg.pm.interfaces.web.module.contractor.constructcont;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.Date;
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

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.module.constructcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

/**
 *承包人施工联系单
 */
@Controller
@RequestMapping("contractor/{projectId}/constructcont")
public class SpConstructcontCtl {
	
	private static final String PATH = "contractor/constructcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	@Autowired
	ConstructContService constructContService;
	@Autowired
	ContractService contractService;
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
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		final Supplier supplier=sessionUtil.getValue("supplier");
		final Project project=new Project();
		project.setId(projectId);
		
		QuerySpec<ConstructCont> query=new QuerySpec<ConstructCont>(){
			@Override
			public List<Predicate> criterias(CriteriaBuilder builder, Root<ConstructCont> from) {
				List<Predicate> criterias=new ArrayList<Predicate>();
				criterias.add(builder.equal(from.get("constructKey").get("project"), project));
				criterias.add(builder.equal(from.get("constructKey").get("supplier"), supplier));
				return criterias;
			}

			@Override
			public List<Order> orders(CriteriaBuilder builder, Root<ConstructCont> from) {
				List<Order> orders=new ArrayList<Order>();
				orders.add(builder.desc(from.<Object>get("createdate")));
				return orders;
			}
		};
		
		List<ConstructCont> constructContList = constructContService.query(query);
		map.put("constructContList", constructContList);
		return Page.LIST;
	}
	
	
	@RequestMapping(value="{constructContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		Project project = projectService.find(projectId);
		Supplier supplier=sessionUtil.getValue("supplier");
		User user = sessionUtil.getValue("user");
		ConstructCont constructCont = constructContId.longValue()>0?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.new_,false);
		map.put("constructCont", constructCont);
		List<Contract> contractList = contractService.findByProjectAndSupplierAndType(constructCont.getConstructKey().getProject(),supplier,ContractType.construct);
		map.put("contractList", contractList);
		map.put("contract", constructCont.getConstructKey().getContract());
		
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long constructContId,@RequestParam("afteraction")String afteraction,@RequestParam(value="constructContItemsId",required=false) Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = projectService.find(projectId);
		User user = sessionUtil.getValue("user");
		
		ConstructCont constructCont =constructContId!=null?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.saved,true);
		 
		Map<Long,ConstructContItem> constructContItemMap=getConstructContItemMap(constructCont.getConstructContItems());	
		List<ConstructContItem> ConstructContItemList = new ArrayList<ConstructContItem>();
		for (int i = 0,len=constructContItemsId==null?0:constructContItemsId.length; i < len; i++) {
			ConstructContItem constructContItem = constructContItemsId[i]>0?constructContItemMap.get(constructContItemsId[i]):new ConstructContItem();
			ConstructContItemList.add(constructContItem);
		}
		constructCont.setConstructContItems(ConstructContItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCont);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		//TODO:constructKey 补充填入供应商字段
		ConstructKey constructKey = constructCont.getConstructKey();
		if(constructKey.getContract()!=null){
			Contract contract = contractService.find(constructKey.getContract().getId());
			constructKey.setSupplier(contract.getSupplier());
		}
		constructCont=constructContService.save(constructCont);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCont.setState(ConstructContState.commit);
			constructCont=constructContService.save(constructCont);
			commit(constructCont, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}
	
	private Map<Long, ConstructContItem> getConstructContItemMap(List<ConstructContItem> constructContItems) {
		Map<Long,ConstructContItem> map = new HashMap<Long,ConstructContItem>();
		if(constructContItems==null) return map;
		for (ConstructContItem constructContItem : constructContItems) {
			map.put(constructContItem.getId(), constructContItem);
		}
		return map;
	}
	
	/**
	 * 提交联系单
	 * @param constructCont
	 * @param user
	 */
	private void commit(ConstructCont constructCont, User user) {
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	

	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructContId") Long constructContId){
		constructContService.delete(constructContId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructContId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructContId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		List<Contract> contractList = contractService.findByProject(constructCont.getConstructKey().getProject());
		map.put("contractList", contractList);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("opinionList", opinionList);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("constructContItemsId") Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(id);
		Map<Long,ConstructContItem> constructContItemMap=getConstructContItemMap(constructCont.getConstructContItems());
		
		List<ConstructContItem> ConstructContItemList = new ArrayList<ConstructContItem>();
		for (int i = 0,len=constructContItemsId.length; i < len; i++) {
			ConstructContItem constructContItem = constructContItemsId[i]>0?constructContItemMap.get(constructContItemsId[i]):new ConstructContItem();
			ConstructContItemList.add(constructContItem);
		}
		constructCont.setConstructContItems(ConstructContItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCont);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		constructCont=constructContService.save(constructCont);
		
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
