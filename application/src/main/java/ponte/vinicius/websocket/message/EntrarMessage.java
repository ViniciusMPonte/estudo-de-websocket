package ponte.vinicius.websocket.message;

public class EntrarMessage extends SalaMessage {
    private String apelido;
    private String uuid;

    public EntrarMessage() {}

    public EntrarMessage(String chave, String apelido, String uuid) {
        super(chave);
        this.apelido = apelido;
        this.uuid = uuid;
    }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
}