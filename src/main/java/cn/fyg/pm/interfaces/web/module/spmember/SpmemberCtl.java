package cn.fyg.pm.interfaces.web.module.spmember;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.SpmemberService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("spmember")
public class SpmemberCtl {
	
	private static final String PATH = "spmember/";
	private interface Page {
		String LIST = PATH + "list";
	}
	
	@Autowired
	SupplierService supplierService;
	@Autowired
	UserService userService;
	@Autowired 
	SpmemberService spmemberService;
	@Autowired
	SpmemberFacade spmemberFacade;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		
		List<User> users = userService.findAll();
		Map<User, Supplier> userSupplier = this.spmemberService.getAllUserSupplier();
		List<SpmemberDto> spmemberDtos = SpmemberAssembler.build(users, userSupplier);
		map.put("spmemberDtos", spmemberDtos);
		
		List<Supplier> constructSupplier = supplierService.findByType(Supptype.contra);
		map.put("constructSupplier", constructSupplier);
		
		return Page.LIST;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(SpmemberPage page,RedirectAttributes redirectAttributes){
		List<SpmemberDto> spmembers = page.getSpmembers();
		spmemberFacade.saveSpmembers(spmembers);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

}
