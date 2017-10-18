package cn.promore.bf.websocket;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import cn.promore.bf.serivce.NotifyService;
import cn.promore.bf.unit.WebSocketUtil;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:2017年4月13日 下午7:40:21
 **/
@Component
public class WebsocketEndPoint extends TextWebSocketHandler  implements WebSocketHandler{
	private Logger logger = Logger.getLogger(getClass());  
	
	@Resource
	NotifyService notifyService;// = ContextLoader.getCurrentWebApplicationContext().getBean(NotifyService.class)
	private static Integer onlineCount = 0 ;
	private static HashMap<String,WebSocketSession> onlineWebSocket = new HashMap<String,WebSocketSession>();
	
    @Override  
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {  
        super.handleTextMessage(session, message);  
    }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionLogin(session);
		ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute("__ONLINE_WEBSOCKET__", onlineWebSocket);
		WebSocketUtil.sendMsgToAllUser("{\"onlineNumber\":"+onlineCount+"}");
		logger.debug("--- WebSocket connected! Online Users:"+getOnelineSessionNumbers()+"--- ");
		System.out.println("--- WebSocket connected! Online Users:"+getOnelineSessionNumbers()+"--- ");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionLogout(session);
		ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute("__ONLINE_WEBSOCKET__", onlineWebSocket);
		WebSocketUtil.sendMsgToAllUser("{\"onlineNumber\":"+onlineCount+"}");
		logger.debug("--- WebSocket closed! Online Users:"+getOnelineSessionNumbers()+"--- ");
		System.out.println("--- WebSocket closed! Online Users:"+getOnelineSessionNumbers()+"--- ");
	}  
    
	
	
	public static synchronized int getOnelineSessionNumbers() {
		return onlineCount;
	}
	public static synchronized void sessionLogin(WebSocketSession session) {
		if(null!=session&&null!=session.getPrincipal()&&StringUtils.isNotEmpty(session.getPrincipal().getName())){
			onlineWebSocket.put(session.getPrincipal().getName(),session);
			WebsocketEndPoint.onlineCount++;	
		}

	}
	public static synchronized void sessionLogout(WebSocketSession session) {
		if(null!=session&&null!=session.getPrincipal()&&StringUtils.isNotEmpty(session.getPrincipal().getName())){
			onlineWebSocket.remove(session.getPrincipal().getName());
			WebsocketEndPoint.onlineCount--;
		}	
	}
    
}
