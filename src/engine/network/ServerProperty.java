package engine.network;

import engine.network.connection.ConnectionListener;
import engine.network.server.ServerListener;
import engine.network.server.ServerType;

public class ServerProperty {
    private String ip;
    private int port;
    private ConnectionListener listener;
    private ServerType type;
    public ServerProperty(String ip, int port, ConnectionListener listener, ServerType type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.listener = listener;
    }

    public ServerType getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ConnectionListener getListener() {
        return listener;
    }
}
