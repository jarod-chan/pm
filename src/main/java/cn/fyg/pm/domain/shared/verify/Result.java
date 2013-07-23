package cn.fyg.pm.domain.shared.verify;

public interface Result {
	
	/**
	 * 该校验是否通过
	 * @return
	 */
	boolean isPass();
	
	/**
	 * 取pass的相反值
	 * @return
	 */
	boolean notPass();
	
	/**
	 * 返回错误信息
	 * @return
	 */
	String message();

}
