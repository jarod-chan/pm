package cn.fyg.pm.domain.model.design.designcont.reason;

import java.util.ArrayList;
import java.util.List;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum Reason implements CommonEnum {
	
	sjcl("A","设计错漏",Type.design),
	bctz("B","补充图纸",Type.design),
	ecsj("C","二次设计",Type.design),
	xctjbh("D","现场条件变化",Type.design),
	ccbmb("E","超成本目标",Type.design),
	
	sgcw("A","施工错误",Type.construct),
	sgkn("B","施工困难",Type.construct),
	sgjdyq("C","施工进度要求",Type.construct),
	
	khxq("A","客户需求",Type.sale),
	chbcxg("B","策划补充修改该",Type.sale),

	ldyq("A","领导要求",Type.other),
	zfyq("B","政府要求",Type.other),
	qt("C","其它",Type.other);
	
	private final String en;//显示序号
	private final String name;//显示名称
	private final Type type;
	
	private Reason(String en,String name,Type type) {
		this.en=en;
		this.name=name;
		this.type=type;
	}
	

	public Type getType() {
		return type;
	}

	public String getEn() {
		return en;
	}


	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * 返回类型原因列表
	 * @return
	 */
	public static List<ReasonItem> getReasonItemList(){
		ArrayList<ReasonItem> reasonItems = new ArrayList<ReasonItem>();
		ReasonItem reasonItem=new ReasonItem();
		for(Reason reason:values()){
			if(reason.getType()!=reasonItem.getType()){
				reasonItem=new ReasonItem();
				reasonItem.setType(reason.getType());
				reasonItems.add(reasonItem);
			}
			reasonItem.appendReason(reason);
		}
		return reasonItems;
	}
}
