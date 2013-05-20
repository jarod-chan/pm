package cn.fyg.pm.domain.model.constructcert;

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
import cn.fyg.pm.interfaces.web.module.constructcert.query.CertQuery;

public class ConstructCertRepositoryImpl implements ConstructCertRepositoryPlus {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ConstructCert> queryList(Project project, CertQuery certQuery) {
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructCert> query=builder.createQuery(ConstructCert.class);
		Root<ConstructCert> from = query.from(ConstructCert.class);
		List<Predicate> criterias=new ArrayList<Predicate>();
		if(project!=null){
			criterias.add(builder.equal(from.get("constructKey").get("project"), project));
		}
		if(StringUtils.isNotBlank(certQuery.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+certQuery.getNo().trim()+"%"));
		}
		if(certQuery.getSupplier()!=null){
			if(certQuery.getSupplier().getId()!=null){
				criterias.add(builder.equal(from.get("constructKey").get("supplier"), certQuery.getSupplier()));
			}
		}
		if(certQuery.getCreatedate_beg()!=null){
			criterias.add(builder.greaterThanOrEqualTo(from.<Date>get("createdate"), certQuery.getCreatedate_beg()));
		}
		if(certQuery.getCreatedate_end()!=null){
			Date nextday=DateUtil.nextDay(certQuery.getCreatedate_end());
			criterias.add(builder.lessThanOrEqualTo(from.<Date>get("createdate"),nextday));
		}
		if(certQuery.getState()!=null){
			Path<Object> statePath = from.get("state");
			String mapValue=certQuery.getState().getMapValue();
			mapState(builder, criterias, statePath, mapValue);
			
		}
		
		List<Order> orders=new ArrayList<Order>();
		String[] attrs = certQuery.getOrderAttribute().split("\\.");
		if(attrs.length>0){
			Path<Object> path = from.get(attrs[0]);
			for(int i=1,len=attrs.length;i<len;i++){
				path=path.get(attrs[i]);
			}
			if(certQuery.getOrderType().toString().equals("asc")){
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

	private void mapState(CriteriaBuilder builder, List<Predicate> criterias,
			Path<Object> statePath, String mapValue) {
		if(mapValue.equals("ext-all")){
			return;
		}
		if(mapValue.equals("ext-notf")){
			criterias.add(builder.notEqual(statePath, ConstructCertState.finish));
			return;
		}
		criterias.add(builder.equal(statePath,ConstructCertState.valueOf(mapValue)));
		
	}

}
