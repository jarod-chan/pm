package cn.fyg.pm.infrastructure.tool.fmt;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fmt {
	
	public static String toStr(Object obj){
		return obj==null?"":obj.toString();
	}
	
	public static String toStr(Date date,String format){
		if(date==null) return "";
		 SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		 return dateFormat.format(date);
	}
	
	public static String toStr(BigDecimal bigDecimal,String format){
		if(bigDecimal==null) return "";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(bigDecimal);
	}

}
