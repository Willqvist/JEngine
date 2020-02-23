package engine.network.server;

import engine.network.connection.Connection;
import engine.network.reasons.DisconnectReason;

public interface MasterServerListener {
    void onConnectionEstablished(Connection connection);
    void onConnectionLost(Connection connection, DisconnectReason reason);
}
