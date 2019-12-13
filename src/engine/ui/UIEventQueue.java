package engine.ui;

import engine.ui.event.Event;

import java.util.LinkedList;

public class UIEventQueue {

    private static LinkedList<Event> eventQueue = new LinkedList<>();

    public static void onEvent(Event event) {
        eventQueue.add(event);
    }

    public void update() {

    }

}
