package cn.fyg.pm.domain.model.design.designnoti;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

public class DesignNotiCommitVld extends CommonValidator<DesignNoti> {

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		DesignNoti designNoti = this.obj;
		if(StringUtils.isBlank(designNoti.getReason())){
			result.append("【原因说明】不能为空");
		}
		if(designNoti.getDesignNotiItems()==null||designNoti.getDesignNotiItems().isEmpty()){
			return result.append("问题项目不能为空");
		}
		for (DesignNotiItem designNotiItem : designNoti.getDesignNotiItems()) {
			verifyItem(designNotiItem,result);
		}
		return result;
	}

	private void verifyItem(DesignNotiItem designNotiItem, CommonResult result) {
		String messsage=String.format("序号为%s的联系项目【内容】,【图号】不能为空", designNotiItem.getSn());
		if(StringUtils.isBlank(designNotiItem.getContent())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(designNotiItem.getGraphno())){
			result.append(messsage);
			return;
		}
	}

	@Override
	public void doUpdate() {
	}

}
