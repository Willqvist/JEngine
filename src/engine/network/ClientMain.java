package engine.network;

import engine.network.connection.*;
import engine.network.packet.Packet;
import engine.network.packet.PacketProvider;
import engine.network.packet.packets.KeyStatePacket;
import engine.network.packet.packets.MovementPacket;
import engine.network.reasons.DisconnectReason;
import engine.network.server.MasterServer;
import org.lwjgl.glfw.GLFW;

import java.util.Scanner;

public class ClientMain {
    private static Connection tCon;
    public static void main(String args[]) {
        PacketProvider.getPacket(MovementPacket.class).onRecive((transmitter, data)-> {
            System.out.println("[CLIENT] server: " + transmitter.getUUID() + " sent speed: " + data.getSpeed());
        });
        new MasterConnection("localhost",3333,(master)->{
            tCon = master.openUdpConnection(0, 3331, new ConnectionListener() {
                @Override
                public void onClientConnected(Connection tcpConnection) {
                    System.out.println("[UDP] Client Connected!");
                    /*
                    Scanner s = new Scanner(System.in);
                    while(true) {

                        String a = s.next();
                        if(a.equals("A")) {
                            tcpConnection.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)1));
                        } else if(a.equals("B")) {
                            tcpConnection.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)0));
                        }
                    }

                     */
                }

                @Override
                public void onClientDisconnected(Connection tcpConnection, DisconnectReason reason) {

                }
            });
        });
        Scanner s = new Scanner(System.in);
        while(true) {

            String a = s.next();
            if(a.equals("A")) {
                tCon.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)1));
            } else if(a.equals("B")) {
                tCon.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)0));
            }
        }
        /*
        master.openUdpConnection(0, 3331, new ConnectionListener() {
            @Override
            public void clientConnectedToServer(Connection tcpConnection) {
                System.out.println("[UDP] Client Connected!");
            }

            @Override
            public void clientDisconnectedFromServer(Connection tcpConnection, DisconnectReason reason) {

            }
        });


        ConnectionManager.openTcpConnection(0,"localhost", 3330, new ConnectionListener() {
            @Override
            public void clientConnectedToServer(Connection tcpConnection) {
                System.out.println("[CLIENT] client connected!!");
                System.out.println("sending packet!!");
                tcpConnection.sendPacket(new MovementPacket().setSpeed(10));
                Scanner s = new Scanner(System.in);
                while(true) {

                    String a = s.next();
                    if(a.equals("A")) {
                        tcpConnection.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)1));
                    } else if(a.equals("B")) {
                        tcpConnection.sendPacket(new KeyStatePacket(GLFW.GLFW_KEY_A,(byte)0));
                    }
                }
            }

            @Override
            public void clientDisconnectedFromServer(Connection tcpConnection, DisconnectReason reason) {
                System.out.println("[CLIENT] disconnected: " + reason.getMessage());
            }
        });

         */
    }
}
