package engine.network.connection;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionManager {

    public static TcpConnection openTcpConnection(int connectionID, String ip, int port, ConnectionListener listener) {
        TcpConnection connection = new TcpConnection(ip,port,listener);
        return connection;
    }

    public static TcpConnection openTcpConnection(int connectionID, Socket socket, ConnectionListener listener) {
        TcpConnection connection = new TcpConnection(socket,listener);
        return connection;
    }

    public static TcpConnection openTcpConnection(Socket socket, ConnectionListener listener) {
        TcpConnection connection = new TcpConnection(socket,listener);
        return connection;
    }

    public static UdpConnection openUdpConnection(int connectionID, String ip, int port, ConnectionListener listener) {
        try {
            return new UdpConnection(InetAddress.getByName(ip),port,listener);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public static UdpConnection openUdpConnection(int connectionID, DatagramSocket server, Destination dest, ConnectionListener listener) {
        UdpConnection connection = new UdpConnection(server,dest,listener);
        return connection;
    }

    public static UdpConnection openUdpConnection(DatagramSocket server, Destination dest, ConnectionListener listener) {
        UdpConnection connection = new UdpConnection(server,dest,listener);
        return connection;
    }

    public static TcpConnection getConnection(int connectionID) {
        return null;
    }

}
