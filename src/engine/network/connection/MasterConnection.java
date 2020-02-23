package engine.network.connection;

import engine.network.packet.packets.ConnectionRequestPacket;
import engine.network.packet.packets.KeyPacket;
import engine.network.reasons.DisconnectReason;
import engine.network.server.MasterServer;

import java.util.function.Consumer;

public class MasterConnection {
    private String ip;
    private int port;
    private int key;
    // CREATE CONNECTION!!
    public MasterConnection(String ip, int port, Consumer<MasterConnection> connected) {
        this.ip = ip;
        this.port = port;

        Connection connection = ConnectionManager.openTcpConnection(0, ip, port, new ConnectionListener() {
            @Override
            public void onClientConnected(Connection tcpConnection) {
                System.out.println("[CLIENT MASTER] connected");
            }

            @Override
            public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {

            }
        });
        /*
        connection.onRecive(ConnectionRequestPacket.class,(packet)-> {
            int serverId = packet.getServerID();
            if(packet.isSuccess()) {
                ServerProperty prop = connections.get(serverId);
                if(prop.getType() == ServerType.UDP)
                    ConnectionManager.openUdpConnection(serverId,prop.getIp(),prop.getPort(),prop.getListener());
                if(prop.getType() == ServerType.TCP)
                    ConnectionManager.openTcpConnection(serverId,prop.getIp(),prop.getPort(),prop.getListener());
            }
        });

         */
        connection.onRecive(KeyPacket.class,(packet)-> {
            System.out.println("recived key: " + packet.getKey());
            key = packet.getKey();
            connected.accept(this);
        });

        connection.connect();
    }


    public Connection openUdpConnection(int id, int port, ConnectionListener listener) {
        System.out.println("settings up udp connection");
        Connection connection = ConnectionManager.openUdpConnection(id, ip, port, new ConnectionListener() {
            @Override
            public void onClientConnected(Connection conn) {
                System.out.println("connected to UDP: " + key);
                listener.onClientConnected(conn);
                conn.sendPacket(new ConnectionRequestPacket().setKey(key));
            }

            @Override
            public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {
                listener.onClientDisconnected(tcpConnection,reason);
            }
        });
        connection.connect();
        return connection;
    }

    public Connection openTcpConnection(int id, int port, ConnectionListener listener) {
        System.out.println("settings up tcp connection");
        Connection connection = ConnectionManager.openTcpConnection(id, ip, port, new ConnectionListener() {
            @Override
            public void onClientConnected(Connection conn) {
                System.out.println("connected to TCP: " + key);
                listener.onClientConnected(conn);
                conn.sendPacket(new ConnectionRequestPacket().setKey(key));
            }

            @Override
            public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {
                listener.onClientDisconnected(tcpConnection,reason);
            }
        });
        connection.connect();
        return connection;
    }

}
