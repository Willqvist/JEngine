package engine.network.packet.packets;

import engine.network.packet.Packet;
import engine.network.packet.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisconnectPacket extends Packet<DisconnectPacket> {
    private int id;
    private String reason;

    public DisconnectPacket(int id, String reason) {
        super();
        this.id = id;
        this.reason = reason;
    }

    public DisconnectPacket() {
        super();
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(id);
        outputStream.writeUTF(reason);
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        id = inputStream.readInt();
        reason = inputStream.readUTF();
    }

}
