package cn.fyg.pm.domain.model.construct.constructcont;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

public class ConstructContCommitVld extends CommonValidator<ConstructCont> {

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		ConstructCont constructCont = this.obj;
		if(StringUtils.isBlank(constructCont.getReason())){
			result.append("【原因】不能为空");
		}
		if(constructCont.getPlandate()==null){
			result.append("【计划完成日期】不能为空");
		}
		if(constructCont.getConstructContItems()==null||constructCont.getConstructContItems().isEmpty()){
			return result.append("联系项目不能为空");
		}
		for (ConstructContItem constructContItem : constructCont.getConstructContItems()) {
			verifyItem(constructContItem,result);
		}
		return result;
	}

	private void verifyItem(ConstructContItem constructContItem,
			CommonResult result) {
		String messsage=String.format("序号为%s的联系项目【内容】,【暂定单价】，【暂定数量】，【暂定结算价】不能为空", constructContItem.getSn());
		if(StringUtils.isBlank(constructContItem.getContent())){
			result.append(messsage);
			return;
		}
		if(constructContItem.getPrice()==null){
			result.append(messsage);
			return;
		}
		if(constructContItem.getNumb()==null){
			result.append(messsage);
			return;
		}
		if(constructContItem.getAmount()==null){
			result.append(messsage);
			return;
		}
	}

	@Override
	public void doUpdate() {

	}

}
