package chat;


import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WebSocketChatServlet extends WebSocketServlet {
    final ChatService chatService;
    final long MAX_TIME = 10 * 60 * 1000;

    public WebSocketChatServlet(){
        this.chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(MAX_TIME);
        webSocketServletFactory.setCreator((req, resp) -> new WebSocketChat(chatService));
    }
}
