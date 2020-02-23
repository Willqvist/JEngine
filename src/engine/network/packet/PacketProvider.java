package engine.network.packet;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class PacketProvider {
    private static HashMap<Class, Packet> packets = new HashMap<>();
    private static HashMap<Short, Class> classes = new HashMap<>();
    public static<T extends Packet> T getPacket(Class<T> cls) {
        return (T)packets.get(cls);
    }

    public static Packet getPacket(short id) {
        Class cls = classes.get(id);
        return packets.get(cls);
    }

    public static Packet newPacket(short id) {
        if(!classes.containsKey(id)) return null;
        Class cls = classes.get(id);
        try {
            return (Packet) cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static void addPacket(Packet packet) {
        packets.put(packet.getClass(),packet);
        classes.put(packet.getId(),packet.getClass());
        System.out.println("adding packet: " + packet.getId());
    }

    static {
        List<Packet> packets = PacketClassLoader.load("engine.network.packet.packets");
        for(Packet p : packets) {
            addPacket(p);
        }
    }

}
