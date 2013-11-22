package cn.fyg.pm.infrastructure.tool.encrypt;

public interface SaltGenerator {
	
	/**
	 * 产生密码盐
	 * @return
	 */
	String getSalt();

}