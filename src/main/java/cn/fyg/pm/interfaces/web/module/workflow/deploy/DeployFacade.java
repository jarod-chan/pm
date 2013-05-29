package cn.fyg.pm.interfaces.web.module.workflow.deploy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletContext;

import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.service.WorkflowService;
import cn.fyg.pm.domain.model.workflow.processfile.ProcessFile;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;



@Component
public class DeployFacade {
	
	public static final Logger logger=LoggerFactory.getLogger(DeployFacade.class);
	
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	WorkflowService workflowService;
	
	
	public List<ProcessFile> getProcessFile() throws FileNotFoundException{
		return workflowService.getProcessFile(getProcessFilePath());
	}
	
	private String getProcessFilePath(){
		return servletContext.getRealPath("") + File.separator+ AppConstant.PROCESS_FILE;
	}
	
	public void deployFile(String filename) throws FileNotFoundException {
		workflowService.deployFile(getProcessFilePath(), filename);
	}
	
	
}
