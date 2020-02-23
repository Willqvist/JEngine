package engine.network.connection;

import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;
import engine.network.packet.PacketTransmitter;
import engine.network.packet.packets.ConnectionPacket;
import engine.network.packet.packets.DisconnectPacket;
import engine.network.reasons.DisconnectEnum;
import engine.network.reasons.DisconnectReason;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;
import java.util.function.Consumer;

public class UdpConnection extends Thread implements Connection, PacketTransmitter {

    private ConnectionListener listener;
    private int uuid;
    private Destination destination;
    private DatagramSocket origin;
    private byte[] outData = new byte[1024];
    private byte[] inData = new byte[1024];
    private boolean connected = true;
    protected UdpConnection(DatagramSocket server, Destination destination, ConnectionListener listener) {
        init(server,destination,listener);
    }

    private void init(DatagramSocket origin, Destination destination, ConnectionListener listener) {
        onDisconnectPacketListener();
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
        this.uuid = Objects.hash(destination.getAddress(),destination.getPort());
        this.listener = listener;
    }

    protected UdpConnection(InetAddress ip, int port, ConnectionListener listener) {
        try {
            init(new DatagramSocket(),new Destination(ip,port),listener);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        this.start();
    }

    private void onDisconnectPacketListener() {
        PacketProvider.getPacket(DisconnectPacket.class).onRecive((id, data)-> {
            connected = false;
            listener.onClientDisconnected(this, new DisconnectReason("UdpServer closed!", DisconnectEnum.CONNECTION_LOST));
        });
    }

    @Override
    public void sendPacket(Packet packet) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            packet.write(outputStream);
            outputStream.flush();
            byte[] data = out.toByteArray();
            DatagramPacket dataPacket = new DatagramPacket(data,data.length,destination.getAddress(),destination.getPort());
            origin.send(dataPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endConnection() {
        connected = false;
    }

    @Override
    public void run() {
        super.run();
        sendPacket(new ConnectionPacket());
        this.listener.onClientConnected(this);
        while(connected) {
            DatagramPacket packet = new DatagramPacket(inData,inData.length);
            try {
                origin.receive(packet);
                DataInputStream inStream = new DataInputStream(new ByteArrayInputStream(packet.getData()));
                Packet packetObj = PacketProvider.getPacket(inStream.readShort());
                packetObj.read(inStream);
                packetObj.executeListeners(this);

                try {
                    Packet p = PacketProvider.getPacket(inStream.readShort());
                }catch(ClassCastException e) {
                    System.out.println("WRONG PACKET SENT: not UDP packet!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public InetAddress getAddress() {
        return destination.getAddress();
    }

    @Override
    public int getPort() {
        return destination.getPort();
    }

    @Override
    public void sendBack(Packet packet) {
        sendPacket(packet);
    }

    @Override
    public int getUUID() {
        return uuid;
    }

    @Override
    public Connection getConnection() {
        return this;
    }

    @Override
    public <T extends Packet> void onRecive(Class<T> p, Consumer<T> packetListener) {
        Packet<T> pack = PacketProvider.getPacket(p);
        pack.onRecive((transmitter,packet)->{
            if(transmitter.getUUID() == getUUID()) {
                packetListener.accept(packet);
            }
        });
    }
}
