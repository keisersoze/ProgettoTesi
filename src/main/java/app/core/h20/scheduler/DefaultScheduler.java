package app.core.h20.scheduler;

import app.core.Event;
import app.core.Scheduler;

import java.util.PriorityQueue;

public class DefaultScheduler implements Scheduler {
    private PriorityQueue<Event> eventQueue;

    public DefaultScheduler () {
        this.eventQueue = new PriorityQueue<>();
    }

    /**
     * @return restituisce un evento con remaining time minore
     */
    public Event scheduleEvent () {
        return eventQueue.poll();
    }

    /**
     * aggiunge {@code e} alla coda di eventi in attesa
     *
     * @param e elemento da aggiungere
     */
    public void addEvent (Event e) {
        eventQueue.add(e);
    }

    @Override
    public void removeEvent (Event e) {
        eventQueue.remove(e);
    }

    @Override
    public void clear () {
        eventQueue.clear();
    }


}
