package engine.network.connection;

import java.net.InetAddress;

public class Destination {
    private InetAddress address;
    private int port;

    public Destination(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
