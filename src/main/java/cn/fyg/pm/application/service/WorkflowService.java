package cn.fyg.pm.application.service;

import java.io.FileNotFoundException;
import java.util.List;

import cn.fyg.pm.domain.model.workflow.processfile.ProcessFile;


public interface WorkflowService {
	
	List<ProcessFile> getProcessFile(String directoryPath)throws FileNotFoundException; 
	
	void deployFile(String directoryPath,String filename)throws FileNotFoundException;

}
