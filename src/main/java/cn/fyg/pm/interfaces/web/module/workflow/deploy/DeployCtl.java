package cn.fyg.pm.interfaces.web.module.workflow.deploy;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.domain.model.workflow.processfile.ProcessFile;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;


@Controller
@RequestMapping("workflow/deploy")
public class DeployCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(DeployCtl.class);
	
	private static final String PATH = "workflow/deploy/";
	private interface Page {
		String DEPLOY = PATH + "deploy";
	}
	
	@Autowired
	DeployFacade deployFacade;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toDeploy(Map<String,Object> map,RedirectAttributes redirectAttributes){
		try {
			List<ProcessFile> processFiles = deployFacade.getProcessFile();
			map.put("processFiles", processFiles);
		} catch (FileNotFoundException e) {
			logger.error("file directory not found");
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("无法找到流程文件目录！"));
		}
		return Page.DEPLOY;
	}
	
	@RequestMapping(value = "/{filename}/", method = RequestMethod.POST)
	public String deploy(@PathVariable("filename") String filename,RedirectAttributes redirectAttributes){
		try {
			deployFacade.deployFile(filename);
		} catch (FileNotFoundException e) {
			logger.error("file not found", e);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("文件[%s]不存在！",filename));
			return "redirect:/admin/workflow/deploy";
		} catch(Exception e){
			logger.error("deploy fail", e);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("文件[%s]发布失败！",filename));
			return "redirect:/admin/workflow/deploy";
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("文件[%s]发布成功。",filename));
		return "redirect:/workflow/deploy";
	}

}
