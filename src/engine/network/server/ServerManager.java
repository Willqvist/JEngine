package engine.network.server;

public class ServerManager {

    public static TcpServer newTcpServer(int port, ServerListener listener) {
        TcpServer server = new TcpServer();
        server.startServer(port,listener);
        return server;
    }

    public static UdpServer newUdpServer(int port, ServerListener listener) {
        UdpServer server = new UdpServer();
        server.startServer(port,listener);
        return server;
    }
}
