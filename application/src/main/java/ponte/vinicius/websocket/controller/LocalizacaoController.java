package ponte.vinicius.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ponte.vinicius.websocket.message.EntrarMessage;
import ponte.vinicius.websocket.message.LocalizacaoMessage;
import ponte.vinicius.websocket.sala.SalaManager;
import ponte.vinicius.websocket.message.SalaMessage;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LocalizacaoController {

    @Autowired
    private SalaManager salaManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/entrar")
    public void entrarNaSala(EntrarMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        salaManager.entrar(message.getChave(), sessionId, message.getApelido());

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "ENTROU");
        payload.put("apelido", message.getApelido());

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }

    @MessageMapping("/localizacao")
    public void enviarLocalizacao(LocalizacaoMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String sala = salaManager.getSalaDoCliente(sessionId);

        if (sala == null || !sala.equals(message.getChave())) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "LOCALIZACAO");
        payload.put("apelido", salaManager.getApelido(sessionId));
        payload.put("latitude", message.getLatitude());
        payload.put("longitude", message.getLongitude());

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }

    @MessageMapping("/sair")
    public void sairDaSala(SalaMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String apelido = salaManager.getApelido(sessionId);

        salaManager.sair(sessionId);

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "SAIU");
        payload.put("apelido", apelido);

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }
}