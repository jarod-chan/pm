package cn.fyg.pm.domain.model.supplier;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("supplierNo")
public class SupplierNo extends AbstractPatternFactory<Supplier> {
	
	private class PatternImp extends AbstractPattern<Supplier>{

		@Override
		public String getNo() {
			return this.t.getNo();
		}

		@Override
		public void setNo(String no) {
			this.t.setNo(no);
		}

		@Override
		public Long initLimmit() {
			return Long.valueOf(99999);
		}

		@Override
		public void initNoKey(NoKey noKey, Supplier supplier) {
			noKey.setSys("D");
			noKey.setFlag("GF");
			noKey.setPref(supplier.getType().getCode());
		}
		
	}


	@Override
	public Pattern<Supplier> doCreate(Supplier t) {
		return new PatternImp().init(t);
	}

}
