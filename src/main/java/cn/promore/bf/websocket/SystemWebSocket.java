package cn.promore.bf.websocket;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import cn.promore.bf.serivce.NotifyService;
import cn.promore.bf.unit.WebSocketUtil;

//@ServerEndpoint("/systemWS")
public class SystemWebSocket {
	
	NotifyService notifyService = ContextLoader.getCurrentWebApplicationContext().getBean(NotifyService.class);
	private static Integer onlineCount = 0 ;
	private static HashMap<String,Session> onlineWebSocket = new HashMap<String,Session>();
	private static Log log = LogFactory.getLog(SystemWebSocket.class);
	
	@OnMessage
	public void onMessage(String message,Session session) throws IOException, InterruptedException{
		
		//session.getBasicRemote().sendText("{onlineNumber:"+onlineCount+"}");
		
////		System.out.println(session.getId());
////		System.out.println(session.getQueryString());
//		if(StringUtils.isNotEmpty(message)){
//			//System.out.println("=========="+message);
//			List<Notify> notifies = notifyService.findUnReadedNotify(Integer.valueOf(message));
//			session.getBasicRemote().sendText("用户未读通知数："+notifies.size()+"!");
//		}else{
//			System.out.println("user is null  =============== ");
//		}
		
//		System.out.println("Received:"+message);
//		session.getBasicRemote().sendText("This is the first Server Message!");
//	    int sentMessages = 0;
//        while (sentMessages < 3) {
//            Thread.sleep(5000);
//            session.getBasicRemote().sendText("This is an intermediate server message. Count: " + sentMessages);
//            sentMessages++;
//        }
//
//        // Send a final message to the client
//        session.getBasicRemote().sendText("This is the last server message");
	}
	
	@OnOpen
	public void onOpen(Session session){
		sessionLogin(session);
		ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute("__ONLINE_WEBSOCKET__", onlineWebSocket);
		WebSocketUtil.sendMsgToAllUser("{\"onlineNumber\":"+onlineCount+"}");
		log.debug("--- WebSocket connected! Online Users:"+getOnelineSessionNumbers()+"--- ");
	}
	
	@OnClose
    public void onClose(Session session) {
		sessionLogout(session);
		ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute("__ONLINE_WEBSOCKET__", onlineWebSocket);
		WebSocketUtil.sendMsgToAllUser("{\"onlineNumber\":"+onlineCount+"}");
		log.debug("--- WebSocket closed! Online Users:"+getOnelineSessionNumbers()+"--- ");
    }
	
	public static synchronized int getOnelineSessionNumbers() {
		return onlineCount;
	}
	public static synchronized void sessionLogin(Session session) {
		onlineWebSocket.put(session.getUserPrincipal().getName(),session);
		SystemWebSocket.onlineCount++;
	}
	public static synchronized void sessionLogout(Session session) {
		onlineWebSocket.remove(session.getUserPrincipal().getName());
		SystemWebSocket.onlineCount--;
	}
}
