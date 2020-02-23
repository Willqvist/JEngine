package engine.network.server;

import engine.network.ServerProperty;
import engine.network.Transmitter;
import engine.network.connection.*;
import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;
import engine.network.packet.packets.ConnectionRequestPacket;
import engine.network.packet.packets.KeyPacket;
import engine.network.reasons.DisconnectReason;
import engine.network.reasons.ServerStopReason;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class MasterServer extends TcpServer implements ServerListener{

    private HashMap<Integer, Server> servers = new HashMap<>();
    private HashMap<Integer, Connection> pendingConnections = new HashMap<>();
    // CREATE SERVER
    public MasterServer(int port) {
        super();
        startServer(port,this);
    }

    @Override
    public void onServerStart(Server server) {
        System.out.println("[MASTER] started");
    }

    @Override
    public void onServerEnd(Server server) {

    }

    @Override
    public synchronized void clientConnectedToServer(Connection connection) {
        System.out.println("[MASTER] connection established!:" + connection.getUUID());
        connection.sendPacket(new KeyPacket().setKey(connection.getUUID()));
    }

    @Override
    public void onServerStop(Server server, ServerStopReason reason) {

    }

    public UdpServer newUdpServer(int port, ServerListener listener) {
        UdpServer server = new UdpServer() {
            @Override
            public MasterServer getMaster() {
                return MasterServer.this;
            }

            @Override
            public void addConnection(Connection connection) {
                MasterServer.this.getConnections().put(connection.getUUID(),connection);
                pendingConnections.put(connection.getUUID(),connection);
            }

            @Override
            public Connection getConnection(InetAddress address, int port) {
                Connection c = MasterServer.super.getConnection(address,port);
                if(c != null) return c;
                return pendingConnections.get(Objects.hash(address,port));
            }

            @Override
            public HashMap<Integer, Connection> getConnections() {
                return MasterServer.super.getConnections();
            }

            @Override
            protected boolean hasConnection(int connectionID) {
                return MasterServer.this.getConnections().containsKey(connectionID) || pendingConnections.containsKey(connectionID);
            }

            @Override
            protected void registerPacketListeners() {
            }

            @Override
            public void executePacket(Transmitter transmitter, Packet p) {
                Connection realCon = getConnections().get(transmitter.getUUID());
                super.executePacket(realCon, p);
            }
        };
        server.onRecive(ConnectionRequestPacket.class,(transmitter,packet)-> {
            getConnections().put(transmitter.getUUID(),getConnections().get(packet.getKey()));
            pendingConnections.remove(transmitter.getUUID());
            listener.clientConnectedToServer(getConnections().get(transmitter.getUUID()));
        });
        servers.put(port,server);
        server.startServer(port,listener);
        return server;
    }

    public TcpServer newTcpServer(int port, ServerListener listener) {
        TcpServer server = new TcpServer() {
            @Override
            public MasterServer getMaster() {
                return MasterServer.this;
            }

            @Override
            public void addConnection(Connection connection) {
                MasterServer.this.getConnections().put(connection.getUUID(),connection);
                pendingConnections.put(connection.getUUID(),connection);
            }

            @Override
            public Connection getConnection(InetAddress address, int port) {
                Connection c = MasterServer.super.getConnection(address,port);
                if(c != null) return c;
                return pendingConnections.get(Objects.hash(address,port));
            }

            @Override
            protected boolean hasConnection(int connectionID) {
                return MasterServer.this.getConnections().containsKey(connectionID) || pendingConnections.containsKey(connectionID);
            }


            @Override
            public HashMap<Integer, Connection> getConnections() {
                return MasterServer.super.getConnections();
            }

            @Override
            public void executePacket(Transmitter transmitter, Packet p) {
                Connection realCon = getConnections().get(transmitter.getUUID());
                super.executePacket(realCon, p);
            }
        };

        server.onRecive(ConnectionRequestPacket.class,(transmitter,packet)-> {
            System.out.println("RECIVED PACKET!!");
            getConnections().put(transmitter.getUUID(),getConnections().get(packet.getKey()));
            pendingConnections.remove(transmitter.getUUID());
            listener.clientConnectedToServer(getConnections().get(transmitter.getUUID()));
        });

        servers.put(port,server);
        server.startServer(port, new ServerListener() {
            @Override
            public void clientConnectedToServer(Connection tcpConnection) {
            }

            @Override
            public void clientDisconnectedFromServer(Connection tcpConnection, DisconnectReason reason) {

            }

            @Override
            public void onServerStart(Server server) {

            }

            @Override
            public void onServerEnd(Server server) {

            }

            @Override
            public void onServerStop(Server server, ServerStopReason reason) {

            }
        });
        return server;
    }

    @Override
    public MasterServer getMaster() {
        return null;
    }


}
