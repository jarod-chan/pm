package cn.fyg.pm.interfaces.web.module.trace.purchasereq.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqSpecs;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("{projectId}/purchasereq")
public class PurchaseReqJsCtl {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<PurchaseReqSmp> simpleQuery(@PathVariable("projectId")Long projectId,PurchaseReqJsQuery query){
		Project project=new Project();
		project.setId(projectId);
		Specifications<PurchaseReq> specs=Specifications.where(PurchaseReqSpecs.inProject(project))
				.and(PurchaseReqSpecs.isState(PurchaseReqState.finish))
				.and(PurchaseReqSpecs.noLike(query.getNo()));
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.DESC,"id")));
		Page<PurchaseReq> page = this.purchaseReqService.findAll(specs, pageable);
		return PageDataAssembler.transferData(page, new PurchaseReqTsf());	
	}

}
