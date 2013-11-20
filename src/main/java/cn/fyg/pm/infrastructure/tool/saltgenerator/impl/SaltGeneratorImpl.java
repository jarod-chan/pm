package cn.fyg.pm.infrastructure.tool.saltgenerator.impl;

import java.util.Random;

import org.springframework.stereotype.Component;

import cn.fyg.pm.infrastructure.tool.saltgenerator.SaltGenerator;

/**
 *生成密码盐，防止密码破解
 */
@Component("saltGenerator")
public class SaltGeneratorImpl implements SaltGenerator {
	
	private static char[] ch={
			'a','b','c','d','e',
			'f','g','h','i','j',
			'k','l','m','n','o',
			'p','q','r','s','t',
			'u','v','w','x','y',
			'z','0','1','2','3',
			'4','5','6','7','8',
			'9'};
	
	private static int minSaltLen=4;
	private static int range=3;

	@Override
	public  String getSalt(){
		Random randomLen = new Random();
		int saltLen=minSaltLen+Math.abs(randomLen.nextInt() % range);
		char[] saltChar=new char[saltLen];
		
		Random randomChar = new Random();
		for (int i = 0; i < saltLen; i++) {
			int rd = Math.abs(randomChar.nextInt() % 36);
			saltChar[i]=ch[rd];
		}
		return String.valueOf(saltChar);
	}
	

}
