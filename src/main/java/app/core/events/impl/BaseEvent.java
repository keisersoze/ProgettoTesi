package app.core.events.impl;

import app.SimContext;
import app.core.actions.Action;
import app.core.actions.impl.utility.RescheduleEvent;
import app.core.events.Event;

import java.util.ArrayList;
import java.util.List;

public class BaseEvent implements Event, Comparable<Event> {
    private double time;
    private List<Action> actionList;
    SimContext context;

    public BaseEvent(double time, SimContext context) {
        this.context = context;
        this.time = context.getSimTime()+time;
        actionList = new ArrayList();
    }

    /**
     * confronta la tempo di se stesso con quello di {@code o}
     *
     * @param o evento sul quale è operato il confronto
     * @return un valore che indica l'esito del confronto
     */

    public int compareTo(Event o) {
        return Double.compare(this.getTime(), o.getTime());
    }


    public double getTime() {
        return time;
    }

    @Override
    public SimContext getContext() {
        return context;
    }

    public void updateTime(double time) {
        this.time= context.getSimTime()+time;
    }

    @Override
    public void setInterval(double interval) {
            addAction(new RescheduleEvent(this,interval));
    }

    /**
     *
     */

    public Event tick() {
        for (Action l : actionList) {
            l.execute(context);
        }
        return this;
    }

    /**
     * @param l
     */

    public void addAction(Action l) {
        actionList.add(l);
    }


}
