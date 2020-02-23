package engine.network.packet;

import engine.input.InputListener;
import engine.network.connection.Connection;
import engine.network.connection.TcpConnection;
import engine.network.packet.packets.KeyStatePacket;

import java.util.function.Consumer;

public class PacketKeyListener implements InputListener, Consumer<KeyStatePacket> {

    private boolean[] keysDown = new boolean[600];

    public PacketKeyListener(Connection c) {
        c.onRecive(KeyStatePacket.class,this);
    }

    @Override
    public void accept(KeyStatePacket keyStatePacket ) {
        keysDown[keyStatePacket.getKey()] = keyStatePacket.getState() == 1 ? true : false;
    }

    @Override
    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

    @Override
    public boolean isKeyUp(int key) {
        return false;
    }

    @Override
    public boolean isMouseUp(int mouse) {
        return false;
    }

    @Override
    public boolean isMouseDown(int mouse) {
        return false;
    }
}
