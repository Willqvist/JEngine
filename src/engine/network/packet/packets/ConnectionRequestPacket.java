package engine.network.packet.packets;

import engine.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectionRequestPacket extends Packet<ConnectionRequestPacket> {

    private boolean success = false;
    private int serverID = 0;
    private int key;

    public int getKey() {
        return key;
    }

    public ConnectionRequestPacket setKey(int key) {
        this.key = key;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ConnectionRequestPacket setServerID(int serverID) {
        this.serverID = serverID;
        return this;
    }

    public int getServerID() {
        return serverID;
    }

    public ConnectionRequestPacket setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        outputStream.writeBoolean(success);
        outputStream.writeInt(serverID);
        outputStream.writeInt(key);
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        success = inputStream.readBoolean();
        serverID = inputStream.readInt();
        key = inputStream.readInt();
    }
}
