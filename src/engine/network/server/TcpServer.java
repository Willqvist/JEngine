package engine.network.server;

import engine.network.Transmitter;
import engine.network.connection.Connection;
import engine.network.connection.ConnectionListener;
import engine.network.connection.TcpConnection;
import engine.network.connection.ConnectionManager;
import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;
import engine.network.packet.packets.DisconnectPacket;
import engine.network.reasons.DisconnectEnum;
import engine.network.reasons.DisconnectReason;
import engine.network.reasons.ServerStopReason;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TcpServer extends Thread implements ConnectionListener, Server {

    private ServerListener listener;
    private ServerSocket socket;
    private HashMap<Integer, Connection> connections = new HashMap<>();
    private boolean running = true;
    protected TcpServer() { }

    @Override
    public boolean startServer(int port, ServerListener listener) {
        this.listener = listener;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            listener.onServerStop(this,new ServerStopReason(e.getMessage(), DisconnectEnum.CRASH));
            return false;
        }
        this.start();
        return true;
    }

    public boolean endServer() {
        HashMap<Integer,Connection> connections = getConnections();
        synchronized (connections) {
            running = false;
            System.out.println("[SERVER] stopping!");
            Iterator<Map.Entry<Integer, Connection>> iterator = connections.entrySet().iterator();
            while(iterator.hasNext()) {
                Connection c = iterator.next().getValue();
                c.sendPacket(new DisconnectPacket(1,"error"));
            }
        }
        return true;
    }

    @Override
    public MasterServer getMaster() {
        return null;
    }

    @Override
    public void addConnection(Connection connection) {
        System.out.println("addign connection!!");
        connections.put(connection.getUUID(),connection);
    }

    @Override
    public Connection getConnection(InetAddress address, int port) {
        return connections.get(Objects.hash(address,port));
}

    public HashMap<Integer,Connection> getConnections() {
        return connections;
    }

    protected boolean hasConnection(int connectionID) {
        return getConnections().containsKey(connectionID);
    }

    public <T extends Packet> void onRecive(Class<T> p, BiConsumer<Transmitter,T> packetListener) {
        Packet<T> pack = PacketProvider.getPacket(p);
        pack.onRecive((transmitter,packet)->{
            if(hasConnection(transmitter.getUUID())) {
                packetListener.accept(transmitter,packet);
            }
        });
    }

    public <T extends Packet> void onRecive(Class<T> p, BiFunction<Transmitter,T,Packet> packetListener) {
        Packet<T> pack = PacketProvider.getPacket(p);
        pack.onRecive((transmitter,packet)->{
            System.out.println("PACKET EXECUTINGS: " + pack);
            if(hasConnection(transmitter.getUUID())) {
                Packet retPack = packetListener.apply(transmitter,packet);
                transmitter.sendBack(retPack);
            }
        });
    }

    @Override
    public void run() {
        super.run();
        this.listener.onServerStart(this);
        while(running) {
            try {
                Socket clientSocket = socket.accept();
                System.out.println("SOCKET CONNECTED");
                TcpConnection c = ConnectionManager.openTcpConnection(clientSocket,this);
                c.connect();
                addConnection(c);
                this.listener.clientConnectedToServer(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listener.onServerStop(this,new ServerStopReason("TcpServer stopped by thread exiting.",DisconnectEnum.DISCONNECT));
    }

    @Override
    public synchronized void onClientConnected(Connection tcpConnection) {
        synchronized (connections) {
            if(!running) {
                tcpConnection.sendPacket(new DisconnectPacket(1,"error"));
            }
            connections.put(tcpConnection.getUUID(), tcpConnection);
        }
    }

    @Override
    public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {

    }

    public void clientDisconnectedFromServer(Connection tcpConnection, DisconnectReason reason) {

    }

}
