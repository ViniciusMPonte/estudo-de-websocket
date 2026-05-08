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
        String apelido = salaManager.getApelido(sessionId);

        salaManager.sair(sessionId);

        if (chave != null) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("evento", "SAIU");
            payload.put("apelido", apelido);

            messagingTemplate.convertAndSend("/topic/sala/" + chave, (Object) payload);
        }
    }
}