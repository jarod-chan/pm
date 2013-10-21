package cn.fyg.pm.domain.model.nogenerator.norecord;

/**
 *非最新编码异常
 *为保持编码完整性，如果删除的对象不是最新编码，则无法删除。
 */
public class NoNotLastException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoNotLastException(String message) {
		super(message);
	}

}
