package cn.fyg.mb.infrastructure.mail;

public interface EmailService {

	void sendMail(String to, String subject, String htmlText)
			throws EmailException;

}
