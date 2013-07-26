package cn.fyg.pm.interfaces.web.module.track.purchasereq;

import java.math.BigDecimal;

import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;

public class ReqItemDto {
	
	private Long id;//id

	private boolean check=false;//是否选中
	
	private boolean readonly=false;//是否只读
	
	private Long sn;//序号
	
	private String metername;//材料名称
	
	private String spec;//规格型号，技术指标
	
	private String unit;//单位
	
	private BigDecimal numb;//数量
	
	private String brand;//推荐品牌

	private String upno;//关联单据类型
	
	private Long upid;//关联单据id
	
	private UptypeEnum uptype;//关联单据类型
	
	private String uptypeName;//关联单据类型名称
	
	/**
	 *完成选中状态
	 */
	public void completeCheck(){
		if(this.upid!=null){
			this.check=true;
			return;
		}
		this.check=false;
	}
	
	/**
	 * 补充类型名称，方便json转换
	 */
	public void completeUptypeName(){
		if(this.uptype!=null){
			this.uptypeName=this.uptype.getName();
		}
	}
	
	/**
	 * 完成只读状态
	 */
	public void completeReadonly(UptypeEnum uptype,Long upid){
		if(this.uptype==null&&this.upid==null){
			this.readonly=false;
			return;
		}
		if(this.uptype==uptype&&this.upid.equals(upid)){
			this.readonly=false;
			return;
		}
		this.readonly=true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public String getMetername() {
		return metername;
	}

	public void setMetername(String metername) {
		this.metername = metername;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUpno() {
		return upno;
	}

	public void setUpno(String upno) {
		this.upno = upno;
	}

	public Long getUpid() {
		return upid;
	}

	public void setUpid(Long upid) {
		this.upid = upid;
	}

	public UptypeEnum getUptype() {
		return uptype;
	}

	public void setUptype(UptypeEnum uptype) {
		this.uptype = uptype;
	}

	public String getUptypeName() {
		return uptypeName;
	}

	public void setUptypeName(String uptypeName) {
		this.uptypeName = uptypeName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getNumb() {
		return numb;
	}

	public void setNumb(BigDecimal numb) {
		this.numb = numb;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	

}
