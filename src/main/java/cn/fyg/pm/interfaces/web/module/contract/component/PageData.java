package cn.fyg.pm.interfaces.web.module.contract.component;

import java.util.List;

public class PageData<T> {
	
	private List<T> content;//当页数据
	
	private int number;//当前页码
	
	private boolean lastPage;//是否最后一页
	

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
