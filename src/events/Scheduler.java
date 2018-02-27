package events;

import java.util.PriorityQueue;

public class Scheduler {
    private PriorityQueue<Event> eventQueue;

    public Scheduler() {
        this.eventQueue = new PriorityQueue<Event>();
    }

    public Event scheduleEvent() {
        return eventQueue.poll();
    }

    public void addEvent(Event e) {
        eventQueue.add(e);
    }


}
