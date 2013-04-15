package cn.fyg.pm.interfaces.web.module.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.pjmember.Pjmember;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.user.User;

@Controller
@RequestMapping("project")
public class ProjectCtl {
	
	private static final String PATH = "project/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String PJMEMBER= PATH +"pjmember";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	PjmemberService pjmemberService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<Project> projectList = projectService.findAll();
		map.put("projectList", projectList);
		return Page.LIST;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(Project project){
		projectService.save(project);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("projectId") Long projectId){
		projectService.delete(projectId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{projectId}/pjmember",method=RequestMethod.GET)
	public String toPjmember(@PathVariable("projectId")Long projectId,Map<String,Object> map){
		Project project=projectService.find(projectId);
		List<User> userList = userService.findAll();
		List<Pjmember> pjmemberList = pjmemberService.findByProject(project);
		Map<String,Pjmember> userKeyToPjmember=new HashMap<String,Pjmember>();
		for (Pjmember pjmember : pjmemberList) {
			userKeyToPjmember.put(pjmember.getUser().getKey(), pjmember);
		}
		List<PjmemberDto> plt=new ArrayList<PjmemberDto>();
		for (User user : userList) {
			Pjmember pjmember=getPjmember(user,project,userKeyToPjmember);
			PjmemberDto pjmemberDto = new PjmemberDto();
			pjmemberDto.setChecked(pjmember.getId()!=null);
			pjmemberDto.setPjmember(pjmember);
			plt.add(pjmemberDto);
		}
		PjmemberPage pjmemberPage=new PjmemberPage();
		pjmemberPage.setPlt(plt);
		map.put("project", project);
		map.put("pjmemberPage", pjmemberPage);
		return Page.PJMEMBER;
	}
	
	private Pjmember getPjmember(User user,Project project,Map<String,Pjmember> userKeyToPjmember){
		Pjmember pjmember=userKeyToPjmember.get(user.getKey());
		if(pjmember!=null) {
			return pjmember;
		}
		pjmember=new Pjmember();
		pjmember.setProject(project);
		pjmember.setUser(user);
		return pjmember;
	}
	
	@RequestMapping(value="{projectId}/pjmember/save",method=RequestMethod.POST)
	public String savePjmember(@PathVariable("projectId")Long projectId,PjmemberPage pjmemberPage){
		Project project=projectService.find(projectId);
		pjmemberService.deleteByProject(project);
		ArrayList<Pjmember> pjmemberList = new ArrayList<Pjmember>();
		for(PjmemberDto pjmemberDto:pjmemberPage.getPlt()){
			if(pjmemberDto.isChecked()){				
				pjmemberList.add(pjmemberDto.getPjmember());
			}
		}
		pjmemberService.save(pjmemberList);
		return "redirect:/project/list";
	}
}
