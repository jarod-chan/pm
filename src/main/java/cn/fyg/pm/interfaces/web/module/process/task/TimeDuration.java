package cn.fyg.pm.interfaces.web.module.process.task;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.infrastructure.tool.date.DateUtil;

/**
 * 时间长度
 */
public class TimeDuration {

	private long value;
	
	private long days;

	private long minutes;

	private long hours;

	public TimeDuration(Date begDate, Date endDate) {
		this.value = DateUtil.durationMinutes(begDate, endDate);
		this.minutes = this.value % 60;
		this.hours = this.value / 60;
		this.days = this.hours / 24;
		this.hours = this.hours % 24;
	}

	public long getMinutes() {
		return minutes;
	}

	public long getHours() {
		return hours;
	}

	public long getDays() {
		return days;
	}
	
	public String getFormatText(){
		String text="";
		text=appendPartString(text,this.days,"天");
		text=appendPartString(text,this.hours,"小时");
		text=appendPartString(text,this.minutes,"分钟");
		if(StringUtils.isBlank(text)){
			return "刚刚";
		}
		return text;
	}
	
	public String appendPartString(String timeText,long timePart,String unit){
		if(StringUtils.isBlank(timeText)){
			if(timePart>0){
				timeText+=(timePart+unit);
			}
		}else{
			timeText+=(timePart+unit);
		}
		return timeText;
	}
	
}
