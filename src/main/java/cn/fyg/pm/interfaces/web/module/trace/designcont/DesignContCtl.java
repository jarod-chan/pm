package cn.fyg.pm.interfaces.web.module.trace.designcont;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.design.TechType;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContItem;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.design.designcont.reason.Reason;
import cn.fyg.pm.domain.model.design.designcont.reason.ReasonItem;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.designcont.query.ContQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
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
	@Autowired
	ContractService contractService;
	@Autowired
	BusifileService busifileService;
	
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
		List<Contract> contractList = this.contractService.findByProjectAndType(project, ContractType.design);
		map.put("contractList", contractList);
		List<ReasonItem> reasonItems = Reason.getReasonItemList();
		map.put("reasonItems", reasonItems);
		if(designCont.getId()!=null){
			BusiCode busiCode=DesignCont.BUSI_CODE;
			Long busiId=designCont.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		map.put("techTypes", TechType.values());
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designContId,@RequestParam("afteraction")String afteraction,@RequestParam(value="designContItemsId",required=false) Long[] designContItemsId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		
		DesignCont designCont =designContId!=null?this.designContService.find(designContId):this.designContService.create(user,project,DesignContState.saved);
		 
		List<DesignContItem> designContItemList = BindTool.changeEntityItems(DesignContItem.class,designCont.getDesignContItems(),designContItemsId);
		designCont.setDesignContItems(designContItemList);
		BindTool.bindRequest(designCont,request);

		designCont=this.designContService.save(designCont);
		

		BusiCode busiCode=DesignCont.BUSI_CODE;
		Long busiId=designCont.getId();
		this.busifileService.associateFile(busiCode, busiId, filestore_id);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		
		if(afteraction.equals("commit")){
			Result result=commit(designCont, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",designCont.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	}


	
	
	
	
	
	

	@Transactional
	private Result commit(DesignCont designCont, User user) {
		Result result = this.designContService.verifyForCommit(designCont);
//		if(result.notPass()) return result;
//		designCont.setState(DesignContState.commit);
//		designCont=.save(purchaseReq);
//		String userKey=user.getKey();
//		try{
//			Map<String, Object> variableMap = new HashMap<String, Object>();
//			variableMap.put(FlowConstant.BUSINESS_ID, purchaseReq.getId());
//			variableMap.put(FlowConstant.APPLY_USER, userKey);
//			identityService.setAuthenticatedUserId(userKey);
//			runtimeService.startProcessInstanceByKey(ReqVarname.PROCESS_DEFINITION_KEY, variableMap);			
//		} finally {
//			identityService.setAuthenticatedUserId(null);
//		}
		return result;
	}
	
	@RequestMapping(value="{designContId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map){
		DesignCont designCont = this.designContService.find(designContId);
		map.put("designCont", designCont);
		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		map.put("filestores", this.busifileService.findFilestores(DesignCont.BUSI_CODE, designContId));
		return Page.VIEW;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("designContId") Long designContId,RedirectAttributes redirectAttributes){
		this.busifileService.removeAssociatedFile(DesignCont.BUSI_CODE, designContId);
		this.designContService.delete(designContId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}

}
