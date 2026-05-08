package ponte.vinicius.websocket.message;

public class SalaMessage {
    private String chave;

    public SalaMessage() {}

    public SalaMessage(String chave) {
        this.chave = chave;
    }

    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }
}