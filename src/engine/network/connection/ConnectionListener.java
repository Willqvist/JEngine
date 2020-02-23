package engine.network.connection;

import engine.network.Transmitter;
import engine.network.packet.Packet;
import engine.network.reasons.DisconnectReason;
import engine.network.server.Server;

public interface ConnectionListener {
    void onClientConnected(Connection tcpConnection);
    void onClientDisconnected(Connection tcpConnection, DisconnectReason reason);
    default void executePacket(Transmitter transmitter, Packet p) {
        p.executeListeners(transmitter);
    }
}
