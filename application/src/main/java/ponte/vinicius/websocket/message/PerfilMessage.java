package ponte.vinicius.websocket.message;

public class PerfilMessage extends SalaMessage {
    private String apelido;

    public PerfilMessage() {}

    public PerfilMessage(String chave, String uuid, String apelido) {
        super(chave, uuid);
        this.apelido = apelido;
    }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
}