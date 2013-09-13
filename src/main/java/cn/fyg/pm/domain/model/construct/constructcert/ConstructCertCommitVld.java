package cn.fyg.pm.domain.model.construct.constructcert;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

/**
 *提交校验
 */
public class ConstructCertCommitVld extends CommonValidator<ConstructCert> {
	
	private ConstructCert conflictConstructCert;
	
	public void setConflictConstructCert(ConstructCert conflictConstructCert) {
		this.conflictConstructCert = conflictConstructCert;
	}

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		ConstructCert constructCert = this.obj;
		if(this.conflictConstructCert!=null){
			result.append("【施工联系单】已经被工程签证单%s关联，无法重复选择",this.conflictConstructCert.getNo());
		}
		if(StringUtils.isBlank(constructCert.getReason())){
			result.append("【原因】不能为空");
		}
		if(constructCert.getConstructCertItems()==null||constructCert.getConstructCertItems().isEmpty()){
			return result.append("签证项目不能为空");
		}
		for (ConstructCertItem constructCertItem : constructCert.getConstructCertItems()) {
			verifyItem(constructCertItem,result);
		}
		return result;
	}

	private void verifyItem(ConstructCertItem constructCertItem, CommonResult result) {
		String messsage=String.format("序号为%s的签证项目【内容】,【结算单价】，【结算数量】，【结算价格】不能为空", constructCertItem.getSn());
		if(StringUtils.isBlank(constructCertItem.getContent())){
			result.append(messsage);
			return;
		}
		if(constructCertItem.getPrice()==null){
			result.append(messsage);
			return;
		}
		if(constructCertItem.getNumb()==null){
			result.append(messsage);
			return;
		}
		if(constructCertItem.getAmount()==null){
			result.append(messsage);
			return;
		}
	}

	@Override
	public void doUpdate() {
		this.obj.getConstructKey().setConstructcert_id(this.obj.getId());
	}

}
