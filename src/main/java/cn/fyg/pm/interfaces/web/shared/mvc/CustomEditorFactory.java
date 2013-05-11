package cn.fyg.pm.interfaces.web.shared.mvc;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomEditorFactory {
	
	public static CustomDateEditor getCustomDateEditor(){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     dateFormat.setLenient(false);
	     return new CustomDateEditor(dateFormat, true);
	}

}
