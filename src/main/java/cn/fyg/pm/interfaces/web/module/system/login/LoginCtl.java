package cn.fyg.pm.interfaces.web.module.system.login;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.SpmemberService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractSpecs;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
public class LoginCtl {
	
	public static final Logger logger = LoggerFactory.getLogger(LoginCtl.class);
	
	private static final String PATH = "system/login/";
	private interface Page {
		String LOGIN = PATH + "login";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	SpmemberService spmemberService;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	ContractService contractService;
	@Autowired
	PjmemberService pjmemberService;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String toLogin(Map<String,Object> map) {
		List<User> users = this.userService.findByEnabled(EnabledEnum.y);
		map.put("users", users);
		return Page.LOGIN;
	}
	
	@RequestMapping(value = "login",method=RequestMethod.POST)
	public String dologin(LoginBean loginBean,HttpServletRequest request,HttpServletResponse response ,RedirectAttributes redirectAttributes) {
		
		UsernamePasswordToken taken = new UsernamePasswordToken(loginBean.getUsername(), loginBean.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try{
			subject.login(taken);
		}catch(AuthenticationException e){
			logger.info("login fail:"+loginBean);
		}
		if(subject.isAuthenticated()){
			User user = this.userService.find(loginBean.getUsername());
			this.sessionUtil.setValue("user", user); //TODO 把应用状态放到cookie中
			if(isSupplierUser(user)){
				return initContractor(user);
			}else{
				return initCompany(user);
			}
		}else{
			loginBean.setPassword("");
			redirectAttributes.addFlashAttribute("loginBean", loginBean);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, "用户名或者密码错误！");	
			return "redirect:/login";
		}
	}
	
	private String initContractor(User user) {
		Supplier supplier=spmemberService.getUserSupplier(user);
		sessionUtil.setValue("supplier", supplier);
		Specifications<Contract> spec=Specifications.where(ContractSpecs.withSupplier(supplier));
		List<Contract> supplierContract = contractService.findAll(spec, new Sort(new Order(Direction.ASC,"id")));
		List<Project> projectList=getContractProject(supplierContract);
		if(projectList!=null && !projectList.isEmpty()){
			sessionUtil.setValue("project", projectList.get(0));
		}
		return "redirect:/fm/contractor/task";
	}
	
	private List<Project> getContractProject(List<Contract> supplierContract) {
		List<Project> projectList= new ArrayList<Project>();
		Set<Long> projectIdSet=new HashSet<Long>();
		for (Contract contract : supplierContract) {
			Project project = contract.getProject();
			Long projectId=project.getId();
			if(!projectIdSet.contains(projectId)){
				projectList.add(project);
				projectIdSet.add(projectId);
			}
		}
		return projectList;
	}
	
	private String initCompany(User user) {
		List<Project> projectList=this.pjmemberService.getUserProject(user);
		if(projectList!=null&&!projectList.isEmpty()){
			sessionUtil.setValue("project", projectList.get(0));
		}
		return "redirect:/fm/company/task";
	}
	
	//判断用户是否承包人
	private boolean isSupplierUser(User user) {
		return this.spmemberService.isUserAssigned(user);
	}
	
	@RequestMapping(value = "logout",method=RequestMethod.POST)
	public String logout(){  
        SecurityUtils.getSubject().logout();  
        return "redirect:/login";  
    }  
	
	@RequestMapping(value = "redirecthome", method = RequestMethod.GET)
	public String redirecthome(){
		Session session = SecurityUtils.getSubject().getSession();
		Object supplier = session.getAttribute("supplier");
		if(supplier!=null){
			return "redirect:/fm/contractor/task";
		}
		return "redirect:/fm/company/task";
	}

}
