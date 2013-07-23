package cn.fyg.pm.domain.shared.verify;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class CommonResult implements Result {
	
	 private List<String> messages=new ArrayList<String>();
	 
	 public CommonResult append(String message){
		 this.messages.add(message);
		 return this;
	 }
	 
	 public CommonResult append(String message,Object...params){
		 this.messages.add(String.format(message, params));
		 return this;
	 }

	@Override
	public boolean isPass() {
		return this.messages.isEmpty();
	}

	@Override
	public String message() {
		if(this.messages.isEmpty()){
			return "";
		}else{
			return Joiner.on("，").join(this.messages)+"。";
		}
	}

	@Override
	public boolean notPass() {
		return !this.messages.isEmpty();
	}

}
