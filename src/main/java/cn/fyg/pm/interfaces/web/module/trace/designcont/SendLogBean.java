package cn.fyg.pm.interfaces.web.module.trace.designcont;


public class SendLogBean {
	
	private String receiver;	//接收人
	
	private Long sendnumb;//发放文件数量

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Long getSendnumb() {
		return sendnumb;
	}

	public void setSendnumb(Long sendnumb) {
		this.sendnumb = sendnumb;
	}
	
	
}
