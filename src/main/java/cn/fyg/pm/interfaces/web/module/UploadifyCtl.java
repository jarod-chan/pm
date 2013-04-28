package cn.fyg.pm.interfaces.web.module;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.fyg.pm.application.FilestoreService;
import cn.fyg.pm.domain.fileupload.filestore.Filestore;

@Controller
@RequestMapping("uploadify")
public class UploadifyCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(UploadifyCtl.class);
	
	@Autowired
	FilestoreService filestoreService;
	@Autowired
	String fileDirectory;
	
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("Filedata") MultipartFile file) {
		
		logger.info("upload file:"+file.getOriginalFilename());
		Filestore filestore = filestoreService.create(file.getOriginalFilename());
		filestore=filestoreService.save(filestore);
		File newFile = new File(fileDirectory +filestore.getId());
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error("保存文件出错", e);
		} catch (IOException e) {
			logger.error("保存文件出错", e);
		}
		return filestore.getId().toString();
	}

}
