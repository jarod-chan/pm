package cn.fyg.pm.interfaces.web.module.workflow.manage;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

import static cn.fyg.pm.interfaces.web.shared.message.Message.*;


@Controller
@RequestMapping("workflow/manage")
public class ManageCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(ManageCtl.class);
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	
	private static final String PATH = "workflow/manage/";
	private interface Page {
		String MANAGE = PATH + "manage";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toManage(Map<String,Object> map){
		List<ProcessDefinition> processes = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().asc().list();
		map.put("processes", processes);
		return Page.MANAGE;
	}
	
	@RequestMapping(value = "{deploymentId}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("deploymentId") String deploymentId,RedirectAttributes redirectAttributes){
		repositoryService.deleteDeployment(deploymentId, true);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("流程[%s]删除成功。",deploymentId));
		return "redirect:/workflow/manage";
	}
	
	@RequestMapping(value = "/{deploymentId}/resource")
	public void loadByDeployment(@PathVariable("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
}
