package engine.network.packet;

import engine.network.Transmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class PacketListeners {
    private static HashMap<Class, ArrayList<BiConsumer<Transmitter,Packet>>> listeners = new HashMap<>();
    private static HashMap<Class, ArrayList<BiFunction<Transmitter,Packet, Packet>>> returnListeners = new HashMap<>();
    public static<T extends Packet> void onReceive(Packet<T> cls, BiConsumer<Transmitter, T> listener) {
        ArrayList<BiConsumer<Transmitter,Packet>> list;
        if(!listeners.containsKey(cls.getClass())) {
            list = new ArrayList<>();
            listeners.put(cls.getClass(),list);
        } else {
            list = listeners.get(cls.getClass());
        }
        list.add((BiConsumer<Transmitter, Packet>) listener);
    }

    public static<T extends Packet> void onReceive(Packet<T> cls, BiFunction<Transmitter,T, Packet> listener) {
        ArrayList<BiFunction<Transmitter,Packet, Packet>> list;
        if(!returnListeners.containsKey(cls.getClass())) {
            list = new ArrayList<>();
            returnListeners.put(cls.getClass(),list);
        } else {
            list = returnListeners.get(cls.getClass());
        }
        list.add((BiFunction<Transmitter,Packet, Packet>)listener);
    }

    public static void executeListeners(Packet packet, Transmitter transmitter) {
        if(listeners.containsKey(packet.getClass())) {
            listeners.get(packet.getClass()).forEach(listener -> listener.accept(transmitter, packet));
        }

        if(returnListeners.containsKey(packet.getClass())) {
            returnListeners.get(packet.getClass()).forEach(listener -> {
                Packet retPacket = listener.apply(transmitter, packet);
                transmitter.sendBack(retPacket);
            });
        }

    }
}
