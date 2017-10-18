package cn.promore.bf.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class MyExecutionListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	// ExecutionListener类的实现
	public void notify(DelegateExecution execution) throws Exception {
		String eventName = execution.getEventName();
		// start
		if ("start".equals(eventName)) {
			System.out.println("start=========");
		} else if ("end".equals(eventName)) {
			System.out.println("end=========");
		} else if ("take".equals(eventName)) {
			System.out.println("take=========");
		}
	}
}
