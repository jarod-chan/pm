package cn.fyg.pm.interfaces.web.module.system.help;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.user.User;


@Controller
@RequestMapping("help")
public class HelpCtl {
	
	private static final String PATH = "system/help/";
	private interface Page {
		String TUSER = PATH + "tuser";
		String UPDATE = PATH + "update";
	}
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "tuser", method = RequestMethod.GET)
	public String toTuser(Map<String,Object> map) {
		Sort sort = new Sort(new Order(Direction.ASC,"key"));
		List<User> users = this.userService.findAll(null, sort);
		map.put("users", users);
		return Page.TUSER;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String toUpdate(Map<String,Object> map) {
		return Page.UPDATE;
	}

}
