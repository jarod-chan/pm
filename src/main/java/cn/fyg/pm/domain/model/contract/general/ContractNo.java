package cn.fyg.pm.domain.model.contract.general;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("contractNo")
public class ContractNo extends AbstractPatternFactory<Contract> {
	
	private class PatternImp extends AbstractPattern<Contract>{

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
			return Long.valueOf(9999);
		}

		@Override
		public void initNoKey(NoKey noKey, Contract contract) {
			noKey.setSys("D");
			noKey.setFlag("HT");
			String projectNo=contract.getProject().getNo();
			String noParts=projectNo.substring(3);
			noKey.setPref(noParts+contract.getType().getCode());
		}
	}

	@Override
	public Pattern<Contract> doCreate(Contract t) {
		return new PatternImp().init(t);
	}

}
