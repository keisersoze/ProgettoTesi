package app.core.scheduler.impl;

import app.core.events.Event;
import app.core.scheduler.Scheduler;

import java.util.PriorityQueue;

public class DefaultScheduler implements Scheduler {
    private PriorityQueue<Event> eventQueue;

    public DefaultScheduler() {
        this.eventQueue = new PriorityQueue<Event>();
    }

    /**
     * @return restituisce un evento con remaining time minore
     */
    public Event scheduleEvent() {
        return eventQueue.poll();
    }

    /**
     * aggiunge {@code e} alla coda di eventi in attesa
     *
     * @param e elemento da aggiungere
     */
    public void addEvent(Event e) {
        eventQueue.add(e);
    }


}
