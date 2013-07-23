package cn.fyg.pm.domain.shared.verify;

public interface Validator<T> {
	
	/**
	 * 设置待校验的主对象
	 * @param obj
	 */
	void setValObject(T obj);
	
	/**
	 * 校验对象
	 * @return
	 */
	Result verify();
	
	/**
	 *执行校验
	 * @return
	 */
	Result doVerify();
	
	/**
	 * 更新校验对领域对象的影响
	 */
	void doUpdate();

}
