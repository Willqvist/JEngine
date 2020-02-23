package engine.network.server;

import engine.network.connection.Connection;
import engine.network.connection.TcpConnection;
import engine.network.reasons.DisconnectReason;
import engine.network.reasons.ServerStopReason;

public class ServerProcessor implements ServerListener {

    @Override
    public void clientConnectedToServer(Connection connection) {

    }

    @Override
    public void clientDisconnectedFromServer(Connection connection, DisconnectReason reason) {

    }

    @Override
    public void onServerStart(Server tcpServer) {

    }

    @Override
    public void onServerEnd(Server tcpServer) {

    }

    @Override
    public void onServerStop(Server tcpServer, ServerStopReason reason) {

    }

}
