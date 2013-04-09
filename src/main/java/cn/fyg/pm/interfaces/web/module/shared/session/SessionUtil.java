package cn.fyg.pm.interfaces.web.module.shared.session;



public interface SessionUtil {

	void setValue(String key, Object value);

	<T> T getValue(String key);

	void invalidate();

}