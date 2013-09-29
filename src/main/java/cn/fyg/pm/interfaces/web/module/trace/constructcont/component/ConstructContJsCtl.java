package cn.fyg.pm.interfaces.web.module.trace.constructcont.component;

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

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContSpecs;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("{projectId}/constructcont")
public class ConstructContJsCtl {
	
	@Autowired
	ConstructContService constructContService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<ConstructContSmp> simpleQuery(@PathVariable("projectId")Long projectId,ConsturctContJsQuery query){
		Project project=new Project();
		project.setId(projectId);
		Specifications<ConstructCont> specs=Specifications.where(ConstructContSpecs.inProject(project))
				.and(ConstructContSpecs.isState(ConstructContState.finish))
				.and(ConstructContSpecs.noLike(query.getNo()));
		
		if(query.getConstructcert_id()!=null){
			specs=specs.and(ConstructContSpecs.canBeSelectByConstructCert(query.getConstructcert_id()));
		}else{
			specs=specs.and(ConstructContSpecs.canBeSelectByConstructCert(-1l));
		}
		if(query.getSupplierId()!=null){
			Supplier supplier=new Supplier();
			supplier.setId(query.getSupplierId());
			specs=specs.and(ConstructContSpecs.withSupplier(supplier));
		}

		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.DESC,"id")));
		Page<ConstructCont> page = this.constructContService.findAll(specs, pageable);
		return PageDataAssembler.transferData(page, new ConstructContTsf());
	}

}
