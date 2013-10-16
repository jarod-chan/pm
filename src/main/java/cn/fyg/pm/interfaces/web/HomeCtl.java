package cn.fyg.pm.interfaces.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeCtl {
	
	private static final Logger logger=LoggerFactory.getLogger(HomeCtl.class);
	
	@RequestMapping(value="home",method=RequestMethod.GET)
	public String toHome(){
		logger.info("java.io.tmpdir:"+System.getProperty("java.io.tmpdir"));
		return "home";
	}

}
