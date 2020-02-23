package engine.network.packet.packets;

import engine.network.packet.Packet;
import engine.network.packet.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MovementPacket extends Packet<MovementPacket> {
    private int speed = 0;

    public MovementPacket setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public MovementPacket() {
        super();
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    protected void writeData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(speed);
    }

    @Override
    protected void readData(DataInputStream inputStream) throws IOException {
        speed = inputStream.readInt();
    }
}
