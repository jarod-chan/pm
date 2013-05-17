package cn.fyg.pm.domain.model.constructcont;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;



import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.infrastructure.tool.DateUtil;
import cn.fyg.pm.interfaces.web.module.constructcont.query.ContQuery;

public class ConstructContRepositoryImpl implements
		ConstructContRepositoryPlus {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ConstructCont> queryList(Project project, ContQuery cquery) {
		
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructCont> query=builder.createQuery(ConstructCont.class);
		Root<ConstructCont> from = query.from(ConstructCont.class);
		List<Predicate> criterias=new ArrayList<Predicate>();
		if(project!=null){
			criterias.add(builder.equal(from.get("constructKey").get("project"), project));
		}
		if(StringUtils.isNotBlank(cquery.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+cquery.getNo().trim()+"%"));
		}
		if(cquery.getCreater()!=null){
			if(StringUtils.isNotBlank(cquery.getCreater().getKey())){
				criterias.add(builder.equal(from.get("creater"), cquery.getCreater()));
			}
		}
		if(cquery.getCreatedate_beg()!=null){
			criterias.add(builder.greaterThanOrEqualTo(from.<Date>get("createdate"), cquery.getCreatedate_beg()));
		}
		if(cquery.getCreatedate_end()!=null){
			Date nextday=DateUtil.nextDay(cquery.getCreatedate_end());
			criterias.add(builder.lessThanOrEqualTo(from.<Date>get("createdate"),nextday));
		}
		if(cquery.getFilterFinish()){
			criterias.add(builder.notEqual(from.get("state"), ConstructContState.finish));
		}
		
		List<Order> orders=new ArrayList<Order>();
		String[] attrs = cquery.getOrderAttribute().split("\\.");
		if(attrs.length>0){
			Path<Object> path = from.get(attrs[0]);
			for(int i=1,len=attrs.length;i<len;i++){
				path.get(attrs[i]);
			}
			if(cquery.getOrderType().toString().equals("asc")){
				orders.add(builder.asc(path));
			}else{
				orders.add(builder.desc(path));
			}
		}
		
		if(!orders.isEmpty()){
			query.orderBy(orders);
		}
		
		if(!criterias.isEmpty()){
			if(criterias.size()==1){
				query.where(criterias.get(0));
			}else{
				query.where(builder.and(criterias.toArray(new Predicate[0])));
			}
		}
		
		return entityManager.createQuery(query).getResultList();
	}


}
