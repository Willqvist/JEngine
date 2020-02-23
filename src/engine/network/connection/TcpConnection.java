package engine.network.connection;

import engine.network.Transmitter;
import engine.network.packet.*;
import engine.network.packet.packets.DisconnectPacket;
import engine.network.packet.packets.KeyStatePacket;
import engine.network.reasons.DisconnectEnum;
import engine.network.reasons.DisconnectReason;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Consumer;

public class TcpConnection extends Thread implements PacketTransmitter, Connection {

    private Socket socket;
    private ConnectionListener listener;
    private boolean connected = true;
    private int uuid;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    protected TcpConnection(Socket socket, ConnectionListener listener) {
        init(socket,listener);
    }

    private void init(Socket socket, ConnectionListener listener) {
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.socket = socket;
        this.uuid = Objects.hash(socket.getInetAddress(),socket.getPort());
        this.listener = listener;

    }

    private void onDisconnectPacketListener() {
        PacketProvider.getPacket(DisconnectPacket.class).onRecive((id, data)-> {
            listener.onClientDisconnected(this, new DisconnectReason("TcpServer closed!", DisconnectEnum.CONNECTION_LOST));
        });
    }

    public Socket getSocket() {
        return this.socket;
    }

    protected TcpConnection(String ip, int port, ConnectionListener listener){
        try {
            onDisconnectPacketListener();
            init(new Socket(ip,port),listener);
        } catch (IOException e) {
            listener.onClientDisconnected(this, new DisconnectReason(e.getMessage(), DisconnectEnum.EXCEPTION));
        }
    }

    public void sendPacket(Packet packet) {
        try {
            packet.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO listener.packetError(...);
        }
    }

    public void disconnect() {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void run() {
        super.run();
        try {
            this.listener.onClientConnected(this);
            while(connected) {
                short packetId = inputStream.readShort();
                Packet p = PacketProvider.newPacket(packetId);
                System.out.println("PACKET RECIVED! " + p);
                p.read(inputStream);
                listener.executePacket(this,p);
            }
        } catch (IOException e) {
            listener.onClientDisconnected(this, new DisconnectReason(e.getMessage(), DisconnectEnum.EXCEPTION));
            return;
        }
        listener.onClientDisconnected(this, new DisconnectReason("TcpConnection thread stopped!", DisconnectEnum.THREAD_END));
    }

    @Override
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    @Override
    public int getPort() {
        return socket.getPort();
    }

    @Override
    public void sendBack(Packet packet) {
        sendPacket(packet);
    }

    public int getUUID() {
        return uuid;
    }

    @Override
    public Connection getConnection() {
        return this;
    }

    @Override
    public int hashCode() {
        return getUUID();
    }

    public <T extends Packet> void onRecive(Class<T> p, Consumer<T> packetListener) {
        Packet<T> pack = PacketProvider.getPacket(p);
        pack.onRecive((transmitter,packet)->{
            if(transmitter.getUUID() == getUUID()) {
                packetListener.accept(packet);
            }
        });
    }

    @Override
    public void connect() {
        this.start();
    }


}
