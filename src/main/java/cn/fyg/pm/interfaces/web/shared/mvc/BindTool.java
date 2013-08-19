package cn.fyg.pm.interfaces.web.shared.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;

import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.shared.EntityItem;

public class BindTool {
	
	/**
	 * 绑定页面请求参数
	 * @param obj
	 * @param request
	 */
	public static void bindRequest(Object obj,HttpServletRequest request) {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(obj);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
	}
	
	/**
	 * 重新组织明细对象，用于接收页面参数
	 * @param entityItemClass
	 * @param entityItems
	 * @param entityItemIds
	 * @return
	 */
	public static <T extends EntityItem> List<T> changeEntityItems(Class<T> entityItemClass,List<T> entityItems,Long[] entityItemIds){
		Map<Long,T> map=new HashMap<Long, T>();
		for (T item : entityItems) {
			map.put(item.getId(), item);
		}
		
		List<T> entityItemList = new ArrayList<T>();
		for (int i = 0,len=entityItemIds==null?0:entityItemIds.length; i < len; i++) {
			T entityItem=entityItemIds[i]>0?map.get(entityItemIds[i]):null;
			if(entityItem==null){
				try {
					entityItem=entityItemClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			entityItemList.add(entityItem);
		}
		return entityItemList;
	}

}
