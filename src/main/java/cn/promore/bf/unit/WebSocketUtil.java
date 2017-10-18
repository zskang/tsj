package cn.promore.bf.unit;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketUtil {
	
	@SuppressWarnings("unchecked")
	public static void sendMsgToUser(String username,String title,String message, String moduleId){
		HashMap<String, WebSocketSession> onlineWebSockets =  (HashMap<String, WebSocketSession>)ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute("__ONLINE_WEBSOCKET__");
		if(null!=onlineWebSockets&&onlineWebSockets.size()>0&&null!=onlineWebSockets.get(username)){
			WebSocketSession session = onlineWebSockets.get(username);
			try {
				session.sendMessage(new TextMessage("{\"title\":\""+title+"\",\"moduleId\":\""+moduleId+"\",\"message\":\""+message+"\"}"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void sendMsgToAllUser(String message) {
		HashMap<String, WebSocketSession> onlineWebSockets =  (HashMap<String, WebSocketSession>)ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute("__ONLINE_WEBSOCKET__");
		if(null!=onlineWebSockets&&onlineWebSockets.size()>0){
			for(String key:onlineWebSockets.keySet()){
				WebSocketSession session = onlineWebSockets.get(key);
				try {
					if(null!=session&&session.isOpen())session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
