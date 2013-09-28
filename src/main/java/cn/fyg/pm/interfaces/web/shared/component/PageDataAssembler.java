package cn.fyg.pm.interfaces.web.shared.component;

import java.util.List;

import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;

public class PageDataAssembler {
	
	public static<F,T> PageData<T> transferData(Page<F> page,Transfer<F,T> transfer){
		List<F> fromContent = page.getContent();
		List<T> toContent = Lists.newArrayList();
		
		for (F f : fromContent) {
			T t=transfer.transfer(f);
			toContent.add(t);
		}
		
		PageData<T> pageData=new PageData<T>();
		pageData.setContent(toContent);
		pageData.setNumber(page.getNumber());
		pageData.setLastPage(page.isLastPage());
		return pageData;
	}

}
