package cn.fyg.mb.interfaces.file;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 普通文件上传功能
 * @author jhon.chen@gmail.com
 *
 */
@Controller
@RequestMapping("general")
public class GeneralCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(GeneralCtl.class);
	
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String toUpload(Map<String,Object> map){
		map.put("message","一般文件上传功能");
		return "file/general";
	}
	
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file,@RequestParam("name")String name ){
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		String fileDirectory="D:\\file-upload\\";
		File newFile=new File(fileDirectory+file.getOriginalFilename());
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error("保存文件出错", e);
		} catch (IOException e) {
			logger.error("保存文件出错", e);
		}
		return "redirect:../general";
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("name")String name){
		System.out.println(name);
		return "redirect:../general";
	}

}
