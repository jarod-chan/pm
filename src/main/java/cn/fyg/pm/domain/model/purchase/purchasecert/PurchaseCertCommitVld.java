package cn.fyg.pm.domain.model.purchase.purchasecert;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.shared.verify.CommonResult;
import cn.fyg.pm.domain.shared.verify.CommonValidator;
import cn.fyg.pm.domain.shared.verify.Result;

public class PurchaseCertCommitVld extends CommonValidator<PurchaseCert> {
	
	private List<PurchaseReqItem> purchaseReqItems;
	
	public void setPurchaseReqItems(List<PurchaseReqItem> purchaseReqItems) {
		this.purchaseReqItems = purchaseReqItems;
	}

	@Override
	public Result doVerify() {
		CommonResult result = newResult();
		PurchaseCert purchaseCert = this.obj;
		if(this.purchaseReqItems==null||this.purchaseReqItems.isEmpty()){
			result.append("【关联采购明细】没有选择");
		}
		if(StringUtils.isBlank(purchaseCert.getDescrp())){
			result.append("【说明】不能为空");
		}
		
		if(purchaseCert.getPurchaseCertItems()==null||purchaseCert.getPurchaseCertItems().isEmpty()){
			return result.append("确认项目不能为空");
		}
		for (PurchaseCertItem purchaseCertItem : purchaseCert.getPurchaseCertItems()) {
			verifyItem(purchaseCertItem,result);
		}
		return result;
	}

	private void verifyItem(PurchaseCertItem purchaseCertItem, CommonResult result) {
		String messsage=String.format("序号为%s的确认项目【材料名称】,【型号规格和技术指标】，【品牌】，【单位】，【数量】，【单价】，【金额】都不能为空", purchaseCertItem.getSn());
		if(StringUtils.isBlank(purchaseCertItem.getMetername())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(purchaseCertItem.getSpec())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(purchaseCertItem.getBrand())){
			result.append(messsage);
			return;
		}
		if(StringUtils.isBlank(purchaseCertItem.getUnit())){
			result.append(messsage);
			return;
		}
		if(purchaseCertItem.getNumb()==null){
			result.append(messsage);
			return;
		}
		if(purchaseCertItem.getPrice()==null){
			result.append(messsage);
			return;
		}
		if(purchaseCertItem.getAmount()==null){
			result.append(messsage);
			return;
		}
	}

	@Override
	public void doUpdate() {

	}

}
