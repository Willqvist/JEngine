package engine.network.server;

import engine.network.Transmitter;
import engine.network.connection.Connection;
import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Server {
    boolean startServer(int port, ServerListener listener);
    boolean endServer();
    MasterServer getMaster();
    void addConnection(Connection connection);
    Connection getConnection(InetAddress address, int port);
    HashMap<Integer,Connection> getConnections();
    <T extends Packet> void onRecive(Class<T> p, BiConsumer<Transmitter,T> packetListener);
}
