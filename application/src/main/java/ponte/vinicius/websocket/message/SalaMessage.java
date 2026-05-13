package ponte.vinicius.websocket.message;

public class SalaMessage {
    private String uuid;
    private String chave;

    public SalaMessage() {}

    public SalaMessage(String uuid, String chave) {
        this.uuid = uuid;
        this.chave = chave;
    }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }
}