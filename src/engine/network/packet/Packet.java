package engine.network.packet;

import engine.network.Transmitter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class Packet<T extends Packet> {

    private short packetID = 0;

    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeShort(getId());
        writeData(outputStream);
    }
    public void read(DataInputStream inputStream) throws IOException {
        readData(inputStream);

    }

    public Packet() {
        packetID = (short)this.getClass().getName().hashCode();
    }

    protected abstract void writeData(DataOutputStream outputStream) throws IOException;
    protected abstract void readData(DataInputStream inputStream) throws IOException;
    public short getId() {
        return packetID;
    }

    public void onRecive(BiConsumer<Transmitter,T> listener) {
        PacketListeners.onReceive(this,listener);
    }

    public void onRecive(BiFunction<Transmitter,T, Packet> listener) {
        PacketListeners.onReceive(this,listener);
    }

    public void executeListeners(Transmitter transmitter) {
        PacketListeners.executeListeners(this,transmitter);
    }
}
