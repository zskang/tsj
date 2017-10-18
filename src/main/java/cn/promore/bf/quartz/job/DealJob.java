package cn.promore.bf.quartz.job;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.hibernate.SQLQuery;

import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.serivce.TaskService;
import cn.promore.bf.unit.WebSocketUtil;

public class DealJob {
	public static Logger LOG = LoggerFactory.getLogger(DealJob.class);
	@Autowired
	SessionFactory sessionFactory;
	@Resource(name="taskService_")
	TaskService taskService;
	
	public void work(){
		LOG.debug("------->异步处理订单程序调度开始<-------");
		if(null!=ContextLoader.getCurrentWebApplicationContext()){
			SiteInfo siteInfo =  (SiteInfo)ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute("siteinfo");
			if(null==siteInfo)return ;
			String exportPath = DealJob.class.getClassLoader().getResource("/").getPath().replace("webapps/ROOT/WEB-INF/classes/","")+"exportDir/";
			String saveFilePath = UUID.randomUUID()+".xls";
			File file  = new File(exportPath);
			if(!file.exists())file.mkdirs();
				WebSocketUtil.sendMsgToUser("--", "系统通知", "您有一个下载任务状态发生变化！请前往<a href=javascript:addTab_('_task_manager','报表下载任务','app-task.html')>任务管理</a>查看！", "");
			}
			
	}
	
	public void executeVoidProcedureSql() throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Transaction tr = session.beginTransaction();
			SQLQuery q = session.createSQLQuery("{call p_project_process_detail()}");
			q.executeUpdate();
		    tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
}
