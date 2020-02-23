package engine.network;

import engine.network.connection.Connection;
import engine.network.packet.Packet;

import java.net.InetAddress;

public interface Transmitter {
    InetAddress getAddress();
    int getPort();

    void sendBack(Packet packet);
    int getUUID();

    Connection getConnection();
}
