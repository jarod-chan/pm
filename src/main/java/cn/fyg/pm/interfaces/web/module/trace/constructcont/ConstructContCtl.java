package cn.fyg.pm.interfaces.web.module.trace.constructcont;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.application.facade.ConstructContFacade;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.constructcont.query.ContQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.flow.FlowUtil;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/constructcont")
public class ConstructContCtl {
	
	private static final String PATH="trace/constructcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String PRINT = PATH + "print";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	
	@Autowired
	ContractService contractService;
	@Autowired
	ConstructContService constructContService;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	UserService userService;
	@Autowired
	ContractService ContractService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	@Qualifier("constructcont")
	FlowUtil flowUtil;
	@Autowired
	ConstructContFacade constructContFacade;
	
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,ContQuery query,Map<String,Object> map){
		Project project =new Project();
		project.setId(projectId);
		query.setProject(project);
		List<ConstructCont> constructContList = constructContService.query(query);
		map.put("constructContList", constructContList);
		map.put("query", query);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.contra,Supptype.construct));
		return Page.LIST;
		
	}

	@RequestMapping(value="{constructContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		Project project =new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCont constructCont = constructContId.longValue()>0?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.new_);
		map.put("constructCont", constructCont);
		map.put("contract", constructCont.getConstructKey().getContract());
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long constructContId,@RequestParam("afteraction")String afteraction,@RequestParam(value="constructContItemsId",required=false) Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project =new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		
		ConstructCont constructCont =constructContId!=null?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.saved);
		 
	
		List<ConstructContItem> constructContItemList=BindTool.changeEntityItems(ConstructContItem.class, constructCont.getConstructContItems(), constructContItemsId);
		constructCont.setConstructContItems(constructContItemList);
		BindTool.bindRequest(constructCont,request);
		
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
			
			Result result = constructContFacade.commit(constructCont, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",constructCont.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	}

	

	

	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructContId") Long constructContId){
		constructContService.delete(constructContId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructContId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="notback",required=false)Boolean notback){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("opinions", opinions);
		map.put("notback", notback);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructContId}/print",method=RequestMethod.GET)
	public String toPrint(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("checkerOpinion", flowUtil.getCheckerOpinion(opinions));
		return Page.PRINT;
	}
	
	@RequestMapping(value="{constructContId}/items",method=RequestMethod.GET)
	@ResponseBody 
	public List<ConstructContItem> loadConstructContItemList(@PathVariable("constructContId")Long constructContId){
		ConstructCont constructCont = constructContService.find(constructContId);
		return constructCont.getConstructContItems();
	}
	
	@RequestMapping(value="{constructContId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable("projectId")Long projectId,@PathVariable(value="constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("opinions", opinions);
		map.put("resultList", ResultEnum.agreeItems());
		map.put("busiCode", ConstructCont.BUSI_CODE);
		return Page.CHECK;
	}
	
	@RequestMapping(value="{constructContId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("opinionList", opinionList);
		
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("constructContItemsId") Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(id);
		
		List<ConstructContItem> constructContItemList=BindTool.changeEntityItems(ConstructContItem.class, constructCont.getConstructContItems(), constructContItemsId);
		constructCont.setConstructContItems(constructContItemList);
		BindTool.bindRequest(constructCont,request);
		
		constructCont=constructContService.save(constructCont);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result =constructContFacade.commitCheck(constructCont,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/checkedit",constructCont.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}	
		}
		
		return "";
	}
	

	
}