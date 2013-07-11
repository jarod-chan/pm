package cn.fyg.pm.interfaces.web.module.supplier;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

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
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.supplier.CreditRank;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.interfaces.web.module.supplier.query.SupplierQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

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
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(SupplierQuery query,@PathVariable("supptype")Supptype supptype,Map<String,Object> map){
		query.setType(supptype);
		List<Supplier> supplierList = supplierService.query(query);
		map.put("supplierList", supplierList);
		map.put("supptype", supptype);
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{supplierId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("supptype")Supptype supptype,@PathVariable("supplierId") Long supplierId,Map<String,Object> map){
		Supplier supplier=supplierId.longValue()>0?supplierService.find(supplierId):supplierService.create();
		map.put("supplier", supplier);
		map.put("supptype", supptype);
		map.put("creditRankList", CreditRank.values());
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
	public String delete(@RequestParam("supplierId") Long supplierId,RedirectAttributes redirectAttributes){
		try {
			supplierService.delete(supplierId);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("操作完成"));
		} catch (NoNotLastException e) {
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info(e.getMessage()));
		}
		return "redirect:list";
	}

}
