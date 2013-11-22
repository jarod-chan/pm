package cn.fyg.pm.infrastructure.tool.encrypt;

public interface Encipher {
	
	String encrypt(String password,String salt);

}
