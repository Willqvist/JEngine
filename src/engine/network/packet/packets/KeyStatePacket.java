package engine.network.packet.packets;

import engine.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class KeyStatePacket extends Packet<KeyStatePacket> {

    private int key;
    byte state;

    public KeyStatePacket(int key, byte state) {
        super();
        this.key = key;
        this.state = state;
    }

    public KeyStatePacket() {
        super();
    }

    public int getKey() {
        return key;
    }

    public byte getState() {
        return state;
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(key);
        outputStream.writeByte(state);
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        key = inputStream.readInt();
        state = inputStream.readByte();
    }

}
