package cn.fyg.pm.interfaces.web.module.trace.contractor.constructcert;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.facade.ConstructCertFacade;
import cn.fyg.pm.domain.model.construct.constructcert.CertItemOpinion;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertSpecs;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.constructcert.ConstructCertAssembler;
import cn.fyg.pm.interfaces.web.module.trace.constructcert.ConstructCertDto;
import cn.fyg.pm.interfaces.web.shared.component.QueryComp;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/contractor/{supplierId}/constructcert")
public class SpConstructcertCtl {
	
	private static final String PATH = "trace/contractor/constructcert/";
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
	@Autowired
	ConstructCertFacade constructCertFacade;
	

	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("supplierId")Long supplierId,@PathVariable("projectId")Long projectId,Map<String,Object> map){
		final Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		final Project project=new Project();
		project.setId(projectId);
		
		QueryComp<ConstructCert> comp=new QueryComp<ConstructCert>();
		comp.addSpec(ConstructCertSpecs.inProject(project));
		comp.addSpec(ConstructCertSpecs.withSupplier(supplier));
		Sort sort = new Sort(new Order(Direction.DESC,"createdate"));
		
		List<ConstructCert> constructCertList = this.constructCertService.findAll(comp.toSpec(),sort);
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		return Page.LIST;
	}
	
	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("supplierId")Long supplierId,@PathVariable("projectId")Long projectId,@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		User user = sessionUtil.getValue("user");

		Project project = projectService.find(projectId);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		 
		ConstructCert constructCert =constructCertId.longValue()>0?constructCertService.find(constructCertId):constructCertService.create(user,project,ConstructCertState.new_) ;
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findConstructContCanBeSelectedSupplier(project,constructCertId,supplier);
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("certItemOpinionList", CertItemOpinion.values());
		return Page.EDIT;
	}

	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("supplierId")Long supplierId,@PathVariable("projectId")Long projectId,@RequestParam("id")Long constructCertId,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		Project project = projectService.find(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCert constructCert = constructCertId!=null?constructCertService.find(constructCertId):constructCertService.create(user, project,ConstructCertState.saved);
		
		List<ConstructCertItem> constructCertItemList =BindTool.changeEntityItems(ConstructCertItem.class, constructCert.getConstructCertItems(), constructCertItemsId);		
		constructCert.setConstructCertItems(constructCertItemList);
		BindTool.bindRequest(constructCert, request);
		
		constructCert=this.constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			if(afteraction.equals("save")){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
				return "redirect:list";
			}
			if(afteraction.equals("commit")){
				Result result=constructCertFacade.commit(constructCert, user);
				if(result.notPass()){
					redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
					return String.format("redirect:%s/edit",constructCert.getId());
				}else{				
					redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
					return "redirect:list";
				}
			}
		}
		
		return "";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructCertId") Long constructCertId){
		constructCertService.delete(constructCertId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructCertId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("supplierId")Long supplierId,@PathVariable("projectId")Long projectId,@PathVariable("constructCertId")Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("constructCert", constructCert);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinions", opinions);
		return Page.VIEW;
	}
	
	
	
	
	
	
	
	@RequestMapping(value="{constructCertId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findConstructContCanBeSelectedSupplier(constructCert.getConstructKey().getProject(),constructCertId,constructCert.getConstructKey().getSupplier());
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinionList", opinionList);
		map.put("certItemOpinionList", CertItemOpinion.values());
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(id);
		
		List<ConstructCertItem> constructCertItemList =BindTool.changeEntityItems(ConstructCertItem.class, constructCert.getConstructCertItems(), constructCertItemsId);		
		constructCert.setConstructCertItems(constructCertItemList);
		BindTool.bindRequest(constructCert, request);
		
		constructCert=this.constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result =constructCertFacade.commitCheck(constructCert,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/checkedit",constructCert.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}
		}
		
		return "";

	}
	

}
