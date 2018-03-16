package evt.scheduler;

import evt.Event;
import evt.scheduler.Scheduler;

import java.util.PriorityQueue;

public class DefaultScheduler implements Scheduler {
    private PriorityQueue<Event> eventQueue;

    public DefaultScheduler() {
        this.eventQueue = new PriorityQueue<Event>();
    }

    /**
     *
     * @return restituisce un evento con remaining time minore
     */
    public Event scheduleEvent() {
        Event e = eventQueue.poll();
        e.tick();
        return e;
    }

    /**
     * aggiunge {@code e} alla coda di eventi in attesa
     * @param e elemento da aggiungere
     */
    public void addEvent(Event e) {
        eventQueue.add(e);
    }


}
