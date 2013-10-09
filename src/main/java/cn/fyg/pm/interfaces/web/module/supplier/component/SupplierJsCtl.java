package cn.fyg.pm.interfaces.web.module.supplier.component;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.SupplierSpecs;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
import cn.fyg.pm.interfaces.web.shared.component.QueryComp;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("supplier")
public class SupplierJsCtl {
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<SupplierSmp> simpleQuery(SupplierJsQuery query){
		
		QueryComp<Supplier> comp=new QueryComp<Supplier>();
		if(StringUtils.isNotBlank(query.getNo())){
			comp.addSpec(SupplierSpecs.noLike(query.getNo()));
		}
		
		if(StringUtils.isNotBlank(query.getName())){
			comp.addSpec(SupplierSpecs.nameLike(query.getName()));
		}
		
		if(query.getTypes()!=null){
			comp.addSpec(SupplierSpecs.typeIn(query.getTypes()));
		}
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.DESC,"id")));
		Page<Supplier> page = this.supplierService.findAll(comp.toSpec(), pageable);
		return PageDataAssembler.transferData(page, new SupplierTsf());
	}

}
