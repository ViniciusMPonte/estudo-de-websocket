package ponte.vinicius.websocket.sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {

    @Autowired
    private SalaManager salaManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String chave = salaManager.getSalaDoCliente(sessionId);
        String uuid = salaManager.getUuid(sessionId);

        salaManager.sair(sessionId);

        if (chave == null || uuid == null) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "SAIU");
        payload.put("uuid", uuid);

        messagingTemplate.convertAndSend("/topic/sala/" + chave, (Object) payload);
    }
}