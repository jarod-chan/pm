package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

public class PurchaseReqCommitVld extends CommonValidator<PurchaseReq> {

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		PurchaseReq purchaseReq=this.obj;
		if(StringUtils.isBlank(purchaseReq.getDescrp())){
			result.append("【说明】不能为空");
		}
		if(purchaseReq.getPlandate()==null){
			result.append("【计划进场日期】不能为空");
		}
		if(purchaseReq.getPurchaseReqItems()==null||purchaseReq.getPurchaseReqItems().isEmpty()){
			return result.append("采购项目不能为空");
		}
		for (PurchaseReqItem purchaseReqItem : purchaseReq.getPurchaseReqItems()) {
			verifyItem(purchaseReqItem,result);
		}
		return result;
	}

	private void verifyItem(PurchaseReqItem purchaseReqItem, CommonResult result) {
		String messsage=String.format("序号为%s的采购项目【材料名称】,【型号规格和技术指标】，【单位】，【数量】不能为空", purchaseReqItem.getSn());
		if(StringUtils.isBlank(purchaseReqItem.getMetername())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(purchaseReqItem.getSpec())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(purchaseReqItem.getUnit())){
			result.append(messsage);
			return;
		}
		if(purchaseReqItem.getNumb()==null){
			result.append(messsage);
			return;
		}
	}

	@Override
	public void doUpdate() {
	}

}
