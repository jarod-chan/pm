package cn.fyg.mb.interfaces.file;

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

/**
 * 采用swfupload 上传文件 添加进度条功能
 * 
 * @author jhon.chen@gmail.com
 * 
 */
@Controller
@RequestMapping("swf")
public class SwfCtl {

	public static final Logger logger = LoggerFactory.getLogger(SwfCtl.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toUpload() {
		return "file/swf";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public void upload(@RequestParam("Filedata") MultipartFile file) {
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
	}

}
