package ponte.vinicius.websocket.message;

public class EntrarMessage extends SalaMessage {

    public EntrarMessage() {}

    public EntrarMessage(String uuid, String chave) {
        super(uuid, chave);
    }
}