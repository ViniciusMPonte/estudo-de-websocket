package ponte.vinicius.websocket.message;

public class EntrarMessage extends SalaMessage {
    private String apelido;

    public EntrarMessage() {}

    public EntrarMessage(String chave, String apelido) {
        super(chave);
        this.apelido = apelido;
    }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
}