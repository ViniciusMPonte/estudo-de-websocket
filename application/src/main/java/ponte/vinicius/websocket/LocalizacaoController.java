package ponte.vinicius.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocalizacaoController {

    @MessageMapping("/enviarLocalizacao")
    @SendTo("/topic/localizacao")
    public LocalizacaoMessage enviarLocalizacao(LocalizacaoMessage message) {
        return message;
    }
}