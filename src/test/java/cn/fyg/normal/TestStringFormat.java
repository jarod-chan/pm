package cn.fyg.normal;

import junit.framework.Assert;

import org.junit.Test;

public class TestStringFormat {
	
	@Test
	public void testSave(){
		String result="redirect:1/checkedit?taskId=2";
		String test=String.format("redirect:%s/checkedit?taskId=%s","1","2");
	    Assert.assertEquals(result, test);
	}

}
