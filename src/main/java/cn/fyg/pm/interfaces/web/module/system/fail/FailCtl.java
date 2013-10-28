package cn.fyg.pm.interfaces.web.module.system.fail;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FailCtl {
	
	
	private static final String PATH = "system/notfound/";
	private interface Page {
		String NOTFOUND = PATH + "notfound";
	}
	
	@RequestMapping(value = "404", method = RequestMethod.GET)
	public String toLogin(Map<String,Object> map) {
		return Page.NOTFOUND;
	}

}
