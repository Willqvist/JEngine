package engine.network.server;

import engine.network.Transmitter;
import engine.network.connection.*;
import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;
import engine.network.packet.packets.ConnectionPacket;
import engine.network.reasons.DisconnectEnum;
import engine.network.reasons.DisconnectReason;
import engine.network.reasons.ServerStopReason;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

public class UdpServer extends Thread implements ConnectionListener, Server {

    private DatagramSocket socket;
    private ServerListener listener;
    private boolean running = true;
    private byte[] dataBuffer = new byte[256];
    private HashMap<Integer, Connection> connections = new HashMap<>();

    protected UdpServer(){}

    @Override
    public void onClientConnected(Connection tcpConnection) {

    }

    @Override
    public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {

    }

    @Override
    public boolean startServer(int port, ServerListener listener) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            listener.onServerStop(this,new ServerStopReason(e.getMessage(), DisconnectEnum.CRASH));
            return false;
        }
        this.listener = listener;
        this.start();
        return true;
    }

    protected int toConnectionID(InetAddress address, int port) {
        return Objects.hash(address,port);
    }

    public void addConnection(Connection connection) {
        connections.put(connection.getUUID(),connection);
    }

    public Connection getConnection(InetAddress address, int port) {
        return connections.get(toConnectionID(address,port));
    }

    @Override
    public HashMap<Integer, Connection> getConnections() {
        return connections;
    }

    protected boolean hasConnection(int connectionID) {
        return getConnections().containsKey(connectionID);
    }

    protected void registerPacketListeners() {
        PacketProvider.getPacket(ConnectionPacket.class).onRecive(((transmitter, connectionPacket) -> {
            this.listener.clientConnectedToServer(transmitter.getConnection());
        }));
    }

    @Override
    public void run() {
        super.run();
        this.listener.onServerStart(this);

        registerPacketListeners();
        while(running) {
            DatagramPacket packet = new DatagramPacket(dataBuffer,dataBuffer.length);
            try {
                socket.receive(packet);
                InetAddress origin = packet.getAddress();
                int port = packet.getPort();
                DataInputStream inStream = new DataInputStream(new ByteArrayInputStream(packet.getData()));
                Connection connection = getConnection(origin,port);
                if(connection == null) {
                    connection = ConnectionManager.openUdpConnection(socket,new Destination(origin,port), this);
                    addConnection(connection);
                }
                Packet packetObj = PacketProvider.getPacket(inStream.readShort());
                packetObj.read(inStream);
                executePacket(connection,packetObj);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public <T extends Packet> void onRecive(Class<T> p, BiConsumer<Transmitter, T> packetListener) {
        Packet<T> pack = PacketProvider.getPacket(p);
        pack.onRecive((transmitter,packet)->{
            if(hasConnection(transmitter.getUUID())) {
                packetListener.accept(transmitter,packet);
            }
        });
    }

    @Override
    public boolean endServer() {
        running = false;
        return true;
    }

    @Override
    public MasterServer getMaster() {
        return null;
    }


}
