package cn.fyg.pm.interfaces.web.shared.session.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

/**
 * session 处理
 */
@Component
public class SessionUtilImpl implements SessionUtil {
	
	@Autowired
	HttpSession httpSession;
	
	@Override
	public void setValue(String key,Object value){
		httpSession.setAttribute(key, value);
	}
	
	@Override
	public <T> T getValue(String key){
		Object obj=httpSession.getAttribute(key);
		if(obj==null) return null;
		@SuppressWarnings("unchecked")
		T returnVal = (T)obj;
		return returnVal;
	}
	
	@Override
	public void invalidate(){
		httpSession.invalidate();
	}

	
}
