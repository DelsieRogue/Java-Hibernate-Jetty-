package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private Set<WebSocketChat> webSockets;

    public ChatService(){
        webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void addWebSocket(WebSocketChat webSocketChat){
        webSockets.add(webSocketChat);
    }

    public void removeWebSocket(WebSocketChat webSocketChat){
        webSockets.remove(webSocketChat);
    }

    public void sendMessage(String data){
        for (WebSocketChat user: webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
