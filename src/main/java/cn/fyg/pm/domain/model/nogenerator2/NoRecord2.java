package cn.fyg.pm.domain.model.nogenerator2;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

@Entity
@Table(name="pm_norecord")
public class NoRecord2 {
	
	public static final Logger logger=LoggerFactory.getLogger(NoRecord2.class);
	
    @EmbeddedId  
	private NoKey noKey;//编码模式
	
	private Long currno;//当前序号
	
	private Long limmit;//最大序号
	
	@Transient
	private boolean isLock=false;
	
	public NoRecord2(){
		super();
	}
	
	public NoRecord2(NoKey noKey, Long currno, Long limmit) {
		super();
		this.noKey = noKey;
		this.currno = currno;
		this.limmit = limmit;
	}
	

	
	

	/**
	 * 回退上一位编号
	 */
	public synchronized void prevNo(String lastNo) throws NoNotLastException{
		while(isLock){
			try {
				wait();
			} catch (Exception e) {
				logger.error("therd wait error",e);
			}
		}
		isLock=true;
		if(!lastNo.equals(currNo())){
			throw new NoNotLastException(String.format("对象编码[%s]不是最新数据，无法删除",lastNo));
		}
		this.currno=this.currno-1;
	}

	/**
	 * 获得下个编号，使编号向下推一位
	 * @return
	 */
	public synchronized String nextNo(){
		while(isLock){
			try {
				wait();
			} catch (Exception e) {
				logger.error("therd wait error",e);
			}
		}
		isLock=true;
		this.currno=this.currno+1;
		if(this.currno.compareTo(this.limmit)>0) throw new RuntimeException(this.toString()+"系统编码越界");
		return currNo();
	}
	
	/**
	 * 获得当前编号
	 * @return
	 */
	private String currNo(){
		int flownoLength=this.limmit.toString().length();
		String flowno=Strings.padStart(this.currno.toString(), flownoLength, '0');
		return Joiner.on("").join(this.noKey.getSys(),this.noKey.getFlag(),this.noKey.getPref(),flowno);
	}
	
	
	public synchronized void freeNo(){
		this.isLock=false;
		this.notifyAll();
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
