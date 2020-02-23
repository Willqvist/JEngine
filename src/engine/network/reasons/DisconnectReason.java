package engine.network.reasons;

public class DisconnectReason implements Reason<DisconnectEnum> {
    private String message;
    private DisconnectEnum type;

    public DisconnectReason(String message, DisconnectEnum type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public DisconnectEnum type() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
