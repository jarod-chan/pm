package cn.fyg.pm.domain.model.design.designcont;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

public class DesignContCommitVld extends CommonValidator<DesignCont> {

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		DesignCont designCont = this.obj;
		if(StringUtils.isBlank(designCont.getContent())){
			result.append("【联系内容】不能为空");
		}
		if(designCont.getReason()==null){
			result.append("【技术变更原因】必选");
		}
		return result;
	}

	@Override
	public void doUpdate() {
	}

}
