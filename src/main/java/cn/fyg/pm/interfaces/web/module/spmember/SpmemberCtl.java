package cn.fyg.pm.interfaces.web.module.spmember;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.service.SpmemberService;
import cn.fyg.pm.application.service.SupplierService;
import cn.fyg.pm.application.service.UserService;
import cn.fyg.pm.domain.model.spmember.Spmember;
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
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<Supplier> constructSupplier = supplierService.findByType(Supptype.contra);
		List<Spmember> spmembers = spmemberService.findAll();
		List<User> users = userService.findAll();
		List<SpmemberDto> spmemberDtos = SpmemberAssembler.build(users, spmembers);
		map.put("spmemberDtos", spmemberDtos);
		map.put("constructSupplier", constructSupplier);
		return Page.LIST;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(SpmemberPage page,RedirectAttributes redirectAttributes){
		List<Spmember> spmembers = page.getSpmembers();
		for (Spmember spmember : spmembers) {
			//TODO 当供应商没有选择时，对象清空
			if(spmember.getSupplier().getId()==null){
				spmember.setSupplier(null);
			}
			
			if(spmember.getId()!=null&&spmember.getSupplier()==null){
				spmemberService.delete(spmember.getId());
			}else if(spmember.getSupplier()!=null){
				spmemberService.save(spmember);
			}
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

}
