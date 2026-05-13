package ponte.vinicius.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ponte.vinicius.websocket.message.EntrarMessage;
import ponte.vinicius.websocket.message.LocalizacaoMessage;
import ponte.vinicius.websocket.message.PerfilMessage;
import ponte.vinicius.websocket.message.SalaMessage;
import ponte.vinicius.websocket.sala.SalaManager;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LocalizacaoController {

    @Autowired
    private SalaManager salaManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private boolean sessaoInvalida(String sessionId, SalaMessage message) {
        String sala = salaManager.getSalaDoCliente(sessionId);
        String uuidServidor = salaManager.getUuid(sessionId);
        return sala == null || !sala.equals(message.getChave())
                || uuidServidor == null || !uuidServidor.equals(message.getUuid());
    }

    @MessageMapping("/entrar")
    public void entrarNaSala(EntrarMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        salaManager.entrar(message.getChave(), sessionId, message.getUuid());

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "ENTROU");
        payload.put("uuid", message.getUuid());

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }

    @MessageMapping("/perfil")
    public void atualizarPerfil(PerfilMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        if (sessaoInvalida(sessionId, message)) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "PERFIL");
        payload.put("uuid", salaManager.getUuid(sessionId));
        payload.put("apelido", message.getApelido());

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave() + "/perfil", (Object) payload);
    }

    @MessageMapping("/localizacao")
    public void enviarLocalizacao(LocalizacaoMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        if (sessaoInvalida(sessionId, message)) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "LOCALIZACAO");
        payload.put("uuid", salaManager.getUuid(sessionId));
        payload.put("latitude", message.getLatitude());
        payload.put("longitude", message.getLongitude());

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }

    @MessageMapping("/sair")
    public void sairDaSala(SalaMessage message, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String uuidServidor = salaManager.getUuid(sessionId);

        salaManager.sair(sessionId);

        if (uuidServidor == null || !uuidServidor.equals(message.getUuid())) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("evento", "SAIU");
        payload.put("uuid", uuidServidor);

        messagingTemplate.convertAndSend("/topic/sala/" + message.getChave(), (Object) payload);
    }
}