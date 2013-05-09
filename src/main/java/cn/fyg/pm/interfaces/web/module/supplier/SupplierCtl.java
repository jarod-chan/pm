package cn.fyg.pm.interfaces.web.module.supplier;

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

import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;

@Controller
@RequestMapping("supplier/{supptype}")
public class SupplierCtl {
	
	private static final String PATH = "supplier/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(@PathVariable("supptype")Supptype supptype,Map<String,Object> map){
		List<Supplier> supplierList = supplierService.findByType(supptype);
		map.put("supplierList", supplierList);
		map.put("supptype", supptype);
		return Page.LIST;
	}
	
	@RequestMapping(value="{supplierId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("supptype")Supptype supptype,@PathVariable("supplierId") Long supplierId,Map<String,Object> map){
		Supplier supplier=supplierId.longValue()>0?supplierService.find(supplierId):supplierService.create();
		map.put("supplier", supplier);
		map.put("supptype", supptype);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("id")Long supplierId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Supplier supplier= supplierId!=null?supplierService.find(supplierId):supplierService.create();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(supplier);
		binder.bind(request);
		supplierService.save(supplier);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("supplierId") Long supplierId){
		supplierService.delete(supplierId);
		return "redirect:list";
	}

}
