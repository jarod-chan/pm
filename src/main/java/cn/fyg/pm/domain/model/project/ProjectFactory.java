package cn.fyg.pm.domain.model.project;


public class ProjectFactory {
	
	public static Project create(){
		Project project=new Project();
		project.setState(ProjectStateEnum.enabled);
		return project;
	}

}
