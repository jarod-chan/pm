package cn.fyg.pm.interfaces.web.module;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("uploadify")
public class UploadifyCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(UploadifyCtl.class);
	
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("Filedata") MultipartFile file) {
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		String fileDirectory = "D:\\file-upload\\";
		File newFile = new File(fileDirectory + file.getOriginalFilename());
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error("保存文件出错", e);
		} catch (IOException e) {
			logger.error("保存文件出错", e);
		}
		return "x";
	}

}
