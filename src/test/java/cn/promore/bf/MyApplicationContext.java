package cn.promore.bf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyApplicationContext extends ClassPathXmlApplicationContext {
	
	public MyApplicationContext(String locations) {
		super(locations);
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String args){
		ApplicationContext applicationContext = new MyApplicationContext("applicationContext-base.xml");
		
	}

	@Override
	protected void initPropertySources() {
		super.initPropertySources();
	}
	
	
	
}

