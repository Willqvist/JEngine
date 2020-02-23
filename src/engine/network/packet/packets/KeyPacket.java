package engine.network.packet.packets;

import engine.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class KeyPacket extends Packet<KeyPacket> {

    private int key;

    public int getKey() {
        return key;
    }

    public KeyPacket setKey(int key) {
        this.key = key;
        return this;
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(key);
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        key = inputStream.readInt();
    }
}
