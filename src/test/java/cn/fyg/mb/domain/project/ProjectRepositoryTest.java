package cn.fyg.mb.domain.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.fyg.mb.domain.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProjectRepositoryTest {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Test
	public void testSave(){
		Project project=new Project();
		project.setNo("no");
		project.setName("name");
		
		User user=new User();
		user.setKey("chenzw");
		project.setUser(user);
		projectRepository.save(project);
		
		System.out.println(project);
	}
}
