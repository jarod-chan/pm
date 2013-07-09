package cn.fyg.pm.domain.model.supplier;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

/**
 *供应商：
 *合同签订对象
 */
@Entity
@Table(name="pm_supplier")
public class Supplier implements NoPatternUnit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	//供应商id
	
	private String no;		//编号
	
	private String name;	//名称
	
	@Enumerated(EnumType.STRING)
	private Supptype type;	// 
	
	@Enumerated(EnumType.STRING)
	private CreditRank creditRank;//信用等级
	
	private String busiLicense;//营业执照
	
	private String busiCode;//企业代码
	
	private String address;//公司地址
	
	private String lealPerson; //法人代表
	
	private String compPhone;//公司电话
	
	private String contact;//联系人
	
	private String contPhone;//联系电话
	
	private String account;//资金帐户
	
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
	public Supptype getType() {
		return type;
	}
	public void setType(Supptype type) {
		this.type = type;
	}

	public CreditRank getCreditRank() {
		return creditRank;
	}
	public void setCreditRank(CreditRank creditRank) {
		this.creditRank = creditRank;
	}
	public String getBusiLicense() {
		return busiLicense;
	}
	public void setBusiLicense(String busiLicense) {
		this.busiLicense = busiLicense;
	}
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLealPerson() {
		return lealPerson;
	}
	public void setLealPerson(String lealPerson) {
		this.lealPerson = lealPerson;
	}
	public String getCompPhone() {
		return compPhone;
	}
	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContPhone() {
		return contPhone;
	}
	public void setContPhone(String contPhone) {
		this.contPhone = contPhone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("D");
		nokey.setFlag("GF");
		nokey.setPref(this.type.getCode());
		Long limit=Long.valueOf(99999);
	    return new NoPattern(nokey,limit);
	}
	
	@Override
	public void setGenerateNo(String generateNo) {
		this.no=generateNo;
	}
	
	@Override
	public String getGenerateNo() {
		return this.no;
	}
	
	
}
