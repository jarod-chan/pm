package cn.fyg.pm.domain.shared.verify;

public abstract class CommonValidator<T> implements Validator<T> {
	
	protected T obj;
	
	protected CommonResult newResult(){
		return new CommonResult();
	}
	
	@Override
	public void setValObject(T obj) {
		this.obj=obj;
	};
	
	@Override
	public Result verify() {
		Result result=this.doVerify();
		if(result.isPass()){			
			this.doUpdate();
		}
		return result;
	}

}
