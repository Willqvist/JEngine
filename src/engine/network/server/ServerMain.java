package engine.network.server;

import engine.network.TestUpdate;
import engine.network.connection.Connection;
import engine.network.packet.packets.KeyStatePacket;

public class ServerMain {
    private TestUpdate test = null;
    public static void main(String args[]) {
        new ServerMain();
    }

    public ServerMain() {
        System.out.println("here");

        MasterServer master = new MasterServer(3333) {
            @Override
            public synchronized void clientConnectedToServer(Connection connection) {
                super.clientConnectedToServer(connection);
                System.out.println("[MASTER_SERVER] client connected!: " + connection.getAddress() + ":"+connection.getPort() + " | " + connection.getUUID());
            }
        };

        UdpServer server = master.newUdpServer(3331, new ServerProcessor() {
            @Override
            public void onServerStart(Server tcpServer) {
                System.out.println("[UDP_SERVER] started!");
            }

            @Override
            public void clientConnectedToServer(Connection connection) {
                System.out.println("[TCP_SERVER] client connected!: " + connection.getAddress() + ":"+connection.getPort() + " | " + connection.getUUID());
            }
        });

        server.onRecive(KeyStatePacket.class,((transmitter, keyStatePacket) -> {
            System.out.println("[TCP_SERVER] key sent["+keyStatePacket.getKey()+" | "+(keyStatePacket.getState()==1)+"]!: " + transmitter.getAddress() + ":"+transmitter.getPort() + " | " + transmitter.getUUID());
        }));

    }
}
