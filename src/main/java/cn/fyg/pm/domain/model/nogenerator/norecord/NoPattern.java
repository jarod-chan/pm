package cn.fyg.pm.domain.model.nogenerator.norecord;


/**
 *保存固定的编码模式
 */
public class NoPattern {
	
	private NoKey noKey;//编码关键字
	
	private Long limmit;//最大序号
	
	
	public NoPattern(NoKey noKey, Long limmit) {
		super();
		this.noKey = noKey;
		this.limmit = limmit;
	}

	public NoKey getNoKey() {
		return noKey;
	}

	public void setNoKey(NoKey noKey) {
		this.noKey = noKey;
	}

	public Long getLimmit() {
		return limmit;
	}

	public void setLimmit(Long limmit) {
		this.limmit = limmit;
	}

}
