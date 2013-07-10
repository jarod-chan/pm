package cn.fyg.pm.domain.model.nogenerator;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

@Entity
@Table(name="pm_norecord")
public class NoRecord {
	
    @EmbeddedId  
	private NoKey noKey;//编码模式
	
	private Long currno;//当前序号
	
	private Long limmit;//最大序号
	
	public NoRecord(){
		super();
	}
	
	public NoRecord(NoKey noKey, Long currno, Long limmit) {
		super();
		this.noKey = noKey;
		this.currno = currno;
		this.limmit = limmit;
	}
	
	/**
	 * 获得当前编号
	 * @return
	 */
	public String generateCurrNo(String separator){
		int flownoLength=this.limmit.toString().length();
		String flowno=Strings.padStart(this.currno.toString(), flownoLength, '0');
		return Joiner.on(separator).join(this.noKey.getSys(),this.noKey.getFlag(),this.noKey.getPref(),flowno);
	}
	
	/**
	 * 回退上一位编号
	 */
	public void generatePrevNo(){
		this.currno=this.currno-1;
	}

	/**
	 * 获得下个编号，使编号向下推一位
	 * @return
	 */
	public String generateNextNo(String separator){
		this.currno=this.currno+1;
		if(this.currno.compareTo(this.limmit)>0) throw new RuntimeException(this.toString()+"系统编码越界");
		return generateCurrNo(separator);
	}

	public NoKey getNoKey() {
		return noKey;
	}

	public void setNoKey(NoKey noKey) {
		this.noKey = noKey;
	}

	public Long getCurrno() {
		return currno;
	}

	public void setCurrno(Long currno) {
		this.currno = currno;
	}

	public Long getLimmit() {
		return limmit;
	}

	public void setLimmit(Long limmit) {
		this.limmit = limmit;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
	

}
