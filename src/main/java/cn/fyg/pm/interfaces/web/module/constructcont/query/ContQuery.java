package cn.fyg.pm.interfaces.web.module.constructcont.query;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.infrastructure.tool.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.CommonQuery;

public class ContQuery extends CommonQuery<ConstructCont> {
	
	@Override
	public List<Predicate> criterias(CriteriaBuilder builder,Root<ConstructCont> from) {
		List<Predicate> criterias=new ArrayList<Predicate>();
		if(this.getProject()!=null){
			criterias.add(builder.equal(from.get("constructKey").get("project"), this.getProject()));
		}
		if(StringUtils.isNotBlank(this.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+this.getNo().trim()+"%"));
		}
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				criterias.add(builder.equal(from.get("constructKey").get("supplier"), this.getSupplier()));
			}
		}
		if(this.getCreatedate_beg()!=null){
			criterias.add(builder.greaterThanOrEqualTo(from.<Date>get("createdate"), this.getCreatedate_beg()));
		}
		if(this.getCreatedate_end()!=null){
			Date nextday=DateUtil.nextDay(this.getCreatedate_end());
			criterias.add(builder.lessThanOrEqualTo(from.<Date>get("createdate"),nextday));
		}
		if(this.getSpecialty()!=null){
			criterias.add(builder.equal(from.get("constructKey").get("contract").get("specialty"), this.getSpecialty()));
		}
		if(this.getState()!=null){
			Path<Object> statePath = from.get("state");
			String mapValue=this.getState().getMapValue();
			mapState(builder, criterias, statePath, mapValue);
			
		}
		return criterias;
	}
	
	private void mapState(CriteriaBuilder builder, List<Predicate> criterias,
			Path<Object> statePath, String mapValue) {
		if(mapValue.equals("ext-all")){
			return;
		}
		if(mapValue.equals("ext-notf")){
			criterias.add(builder.notEqual(statePath, ConstructContState.finish));
			return;
		}
		criterias.add(builder.equal(statePath,ConstructContState.valueOf(mapValue)));
	}


	
	

}
