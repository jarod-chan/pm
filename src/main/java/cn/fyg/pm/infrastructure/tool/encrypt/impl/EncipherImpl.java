package cn.fyg.pm.infrastructure.tool.encrypt.impl;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import cn.fyg.pm.infrastructure.tool.encrypt.Encipher;

@Component("encipher")
public class EncipherImpl implements Encipher {

	@Override
	public String encrypt(String password, String salt) {
		Preconditions.checkNotNull(password,"password is null");
		Preconditions.checkNotNull(salt,"salt is null");
		return new Sha1Hash(password.trim()+salt.trim()).toString();
	}

}
