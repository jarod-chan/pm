package cn.fyg.pm.infrastructure.mail;

public interface EmailService {

	void sendMail(String to, String subject, String htmlText)
			throws EmailException;

}
