package cn.fyg.mb.domain.project;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.fyg.mb.domain.user.User;

/**
 * 项目：
 * 项目基础信息
 */
public class Project {
	
	private Long id;	//项目id
	private String no;		//编号
	private String name;	//项目名称
	private User user;	//项目负责人
	private String landNo;	//土地使用权证号
	private String planNo;	//规划许可证号
	private String constructNo;//施工许可证号
	private String saleNo;	//预售证号
	private Date begDate;	//开工日期
	private Date endDate;	//竣工日期
	private String position; //位置
	private BigDecimal share;	//所占股份(%)
	private BigDecimal investment; //总投资(元)
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLandNo() {
		return landNo;
	}
	public void setLandNo(String landNo) {
		this.landNo = landNo;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getConstructNo() {
		return constructNo;
	}
	public void setConstructNo(String constructNo) {
		this.constructNo = constructNo;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public Date getBegDate() {
		return begDate;
	}
	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public BigDecimal getShare() {
		return share;
	}
	public void setShare(BigDecimal share) {
		this.share = share;
	}
	public BigDecimal getInvestment() {
		return investment;
	}
	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}
	
	
	@Override
	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}

}
