package cn.fyg.pm.interfaces.web.module.shared.message;


public class Message {
	
	private Message(){}
	
	
	private Level level;
	private String message;
	
	
	public static Message info() {
		Message message = new Message();
		message.level = Level.info;
		return message;
	}
	
	public static Message error() {
		Message message = new Message();
		message.level = Level.error;
		return message;
	}
	
	public Message message(String message,Object...args) {
		this.message = String.format(message, args);
		return this;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getMessage() {
		return message;
	}
	
}
