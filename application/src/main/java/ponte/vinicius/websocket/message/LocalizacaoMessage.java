package ponte.vinicius.websocket.message;

public class LocalizacaoMessage extends SalaMessage {
    private double latitude;
    private double longitude;

    public LocalizacaoMessage() {}

    public LocalizacaoMessage(String uuid, String chave, double latitude, double longitude) {
        super(uuid, chave);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}