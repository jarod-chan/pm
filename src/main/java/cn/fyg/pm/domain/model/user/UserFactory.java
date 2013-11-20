package cn.fyg.pm.domain.model.user;

public class UserFactory {
	
	public static User create(){
		User user=new User();
		user.setPassword("123456");
		user.setEnabled(EnabledEnum.y);
		return user;
	}
}
