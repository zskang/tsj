package cn.promore.bf.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:2017年4月13日 下午7:37:17
 **/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(iWebSocketHandler(), "/systemWS").addInterceptors(myInterceptors()).setAllowedOrigins("*");
		registry.addHandler(iWebSocketHandler(), "/systemWS/info","/systemWS/*","/sockjs/systemWS/*").addInterceptors(myInterceptors()).setAllowedOrigins("*").withSockJS();
	}
	
	@Bean
    public WebSocketHandler iWebSocketHandler() {
        return new WebsocketEndPoint();
    }
	
	@Bean  
    public HandshakeInterceptor myInterceptors(){
        return new MyHandshakeInterceptor();
    } 
}
