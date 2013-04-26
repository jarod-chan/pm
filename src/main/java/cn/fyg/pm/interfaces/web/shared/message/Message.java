package cn.fyg.pm.interfaces.web.shared.message;


public class Message {
	
	private Message(){}
	
	
	private Level level;
	private String message;
	
	
	public static Message info(String str,Object...args) {
		Message message = new Message();
		message.level = Level.info;
		message.message = String.format(str, args);
		return message;
	}
	
	public static Message error(String str,Object...args) {
		Message message = new Message();
		message.level = Level.error;
		message.message = String.format(str, args);
		return message;
	}

	public Level getLevel() {
		return level;
	}
	
	public String getMessage() {
		return message;
	}
	
}
