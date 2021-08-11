package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketChat {
    private final ChatService chatService;
    private Session session;

    public WebSocketChat(ChatService chatService){
        this.chatService = chatService;
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        this.session = session;
        chatService.addWebSocket(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        chatService.removeWebSocket(this);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        chatService.sendMessage(message);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
