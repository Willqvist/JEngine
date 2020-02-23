package engine.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ArrayPacket extends Packet<ArrayPacket> {
    private Packet[] packets;
    private ArrayPacket(Packet... packets) {
        this.packets = packets;
    }


    public static ArrayPacket array(Packet... packets) {
        return new ArrayPacket(packets);
    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        writeData(outputStream);
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        for(int i = 0; i < packets.length; i++) {
            packets[i].write(outputStream);
        }
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        // will not read...
    }
}
