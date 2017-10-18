package cn.promore.bf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogTest {
	private static final Logger logger = Logger.getLogger(LogTest.class);

//	@SuppressWarnings("static-access")
//	@Test
//	public void testJdkLoggingConfigPath() {
//		System.out.println(System.getProperty("java.util.logging.config.file"));
//		System.out.println("addivivity:" + logger.getAdditivity());
//		logger.info("invoke test jdk logging config path");
//		logger.info(LogManager.getLogger(LogTest.class).getLevel().toInt());
//
//		LoggerRepository loggerRepository = logger.getRootLogger().getLoggerRepository();
//		Enumeration<?> allCategories = loggerRepository.getCurrentCategories();
//		while (allCategories.hasMoreElements()) {
//
//		}
//
//	}

	@Test
	public void getDevlopName() {
		String workflowName = "aaaa.zip";
		System.out.println(workflowName.substring(0, workflowName.indexOf(".")));
	}
	
	@Test public void testA(){
		String string = "pro_usr_yjxz_hide";
		Pattern pattern =  Pattern.compile("pro_usr_([a-zA-Z0-9]+)_hide");
		Matcher matcher = pattern.matcher(string);
		if(matcher.find()){
			System.out.println(matcher.group(1));
		}
		System.out.println();
	}
}
