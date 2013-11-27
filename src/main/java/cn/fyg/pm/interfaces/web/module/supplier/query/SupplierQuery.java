package cn.fyg.pm.interfaces.web.module.supplier.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.supplier.CreditRank;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.SupplierSpecs;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.interfaces.web.shared.query.core.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.core.impl.AbstractQuerySpec;

public class SupplierQuery  extends AbstractQuerySpec<Supplier> {
	
	private String no;//编号
	
	private String name;//名称
	
	private CreditRank creditRank;//信用等级
	
	private Supptype type;	// 类型
	
	
	public CreditRank[] getCreditRankList(){
		return CreditRank.values();
	}

	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Supptype getType() {
		return type;
	}

	public void setType(Supptype type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreditRank getCreditRank() {
		return creditRank;
	}

	public void setCreditRank(CreditRank creditRank) {
		this.creditRank = creditRank;
	}

	@Override
	public String initOrderAttribute(){
		return "no";
	}
	
	@Override
	public void initOrderAttributeList(List<Qitem> attributeList) {
		attributeList.add(new Qitem("no","编号"));
	}
	
	@Override
	public void doSpec(List<Specification<Supplier>> specs) {
		if(StringUtils.isNotBlank(this.getNo())){
			specs.add(SupplierSpecs.noLike(this.getNo()));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			specs.add(SupplierSpecs.nameLike(name));
		}
		if(this.getCreditRank()!=null){
			specs.add(SupplierSpecs.isCreditRank(this.getCreditRank()));
		}
		
		if(this.getType()!=null){
			specs.add(SupplierSpecs.isType(this.getType()));
		}
	}

}
