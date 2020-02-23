package engine.network.packet;

public enum PacketType {
    DISCONNECT,MOVEMENT;

    public static PacketType get(int ordinal) {
        return PacketType.values()[ordinal];
    }
}
