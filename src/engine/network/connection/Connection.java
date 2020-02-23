package engine.network.connection;

import engine.network.Transmitter;
import engine.network.packet.Packet;
import java.util.function.Consumer;

public interface Connection extends Transmitter {
    void sendPacket(Packet packet);
    int getUUID();

    public <T extends Packet> void onRecive(Class<T> p, Consumer<T> packetListener);

    void connect();
}
