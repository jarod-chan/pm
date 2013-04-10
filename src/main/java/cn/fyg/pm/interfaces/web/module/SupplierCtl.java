package cn.fyg.pm.interfaces.web.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.supplier.Supplier;
import cn.fyg.pm.domain.supplier.Supptype;

@Controller
@RequestMapping("supplier")
public class SupplierCtl {
	
	private static final String PATH = "supplier/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<Supplier> supplierList = supplierService.findAll();
		map.put("supplierList", supplierList);
		return Page.LIST;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		map.put("supptypeList", Supptype.values());
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(Supplier supplier){
		supplierService.save(supplier);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("supplierId") Long supplierId){
		supplierService.delete(supplierId);
		return "redirect:list";
	}

}
