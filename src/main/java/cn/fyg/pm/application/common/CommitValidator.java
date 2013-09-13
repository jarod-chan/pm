package cn.fyg.pm.application.common;

import cn.fyg.pm.domain.shared.verify.Result;

public interface CommitValidator<T> {
	
	Result verifyForCommit(T t);

}
