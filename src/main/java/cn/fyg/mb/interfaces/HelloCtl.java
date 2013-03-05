package cn.fyg.mb.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.mb.domain.user.UserRepository;

@Controller
public class HelloCtl {

	private static final String PATH = "";
	private interface Page {
		String HELLO = PATH + "hello";
	}
	
	@Autowired
	UserRepository userMapper;
	
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String toHello(Map<String,Object> map){
		List<HashMap<String, Object>> userMap = userMapper.getAll();
		map.put("userMap", userMap);
		return Page.HELLO;
	}

}
