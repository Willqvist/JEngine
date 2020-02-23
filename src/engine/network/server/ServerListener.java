package engine.network.server;

import engine.network.connection.Connection;
import engine.network.connection.TcpConnection;
import engine.network.reasons.DisconnectReason;
import engine.network.reasons.ServerStopReason;

public interface ServerListener {
    void clientConnectedToServer(Connection tcpConnection);
    void clientDisconnectedFromServer(Connection tcpConnection, DisconnectReason reason);
    void onServerStart(Server server);
    void onServerEnd(Server server);
    void onServerStop(Server server, ServerStopReason reason);
}
