package cn.fyg.pm.interfaces.web.module.purchasereq;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.UptypeEnum;

public class ReqItemAssembler {
	
	public static final Logger logger=LoggerFactory.getLogger(ReqItemAssembler.class);

	public static List<ReqItemDto> build(List<PurchaseReqItem> purchaseReqItems,UptypeEnum uptype,Long upid) {
		ArrayList<ReqItemDto> reqItemDtoList = new ArrayList<ReqItemDto>();
		try {
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				ReqItemDto reqItemDto = new ReqItemDto();
				PropertyUtils.copyProperties(reqItemDto, purchaseReqItem);
				reqItemDto.completeUptypeName();
				reqItemDto.completeCheck();
				reqItemDto.completeReadonly(uptype, upid);
				reqItemDtoList.add(reqItemDto);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.equals(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.equals(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			logger.equals(e);
		}
		return reqItemDtoList;
	}
	
}
