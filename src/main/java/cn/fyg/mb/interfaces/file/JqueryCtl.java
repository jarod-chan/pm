package cn.fyg.mb.interfaces.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 使用jquery+swfupload实现文件上传
 * @author jhon.chen@gmail.com
 *
 */
@Controller
@RequestMapping("jquery")
public class JqueryCtl {
	
	public static final Logger logger=LoggerFactory.getLogger(JqueryCtl.class);

	public static final int MB = 1024 * 1024;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String toUpload(){
		return "file/jquery";
	}
	
	@RequestMapping(value = "/doupload3", method = RequestMethod.POST)
	public void upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			String uploadDir = "D://file-upload//";
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("uploadfile");//这里是表单的名字，在swfupload.js中this.ensureDefault("file_post_name", "filedata");
			
			InputStream stream = file.getInputStream();
			String fileName = file.getOriginalFilename();
			fileName = new String(fileName.getBytes(),"utf-8");
			String fileNameFull = uploadDir  + fileName;
			OutputStream bos = new FileOutputStream(fileNameFull);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();
			// close the stream
			stream.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//return new ModelAndView("redirect:/mb/demo/upload");//这里要制定返回页，如果返回Null,上传没问题，但是上传完毕后，页面会弹出404错误
		response.getOutputStream().println("200 OK");
	}
	
	
	@RequestMapping(value="doupload4",method=RequestMethod.POST)
	@ResponseBody
	public String upload4(@RequestParam("uploadfile") MultipartFile file ){
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
		return "200 OK";
	}

}
