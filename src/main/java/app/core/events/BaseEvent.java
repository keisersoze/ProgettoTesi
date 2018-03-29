package app.core.events;

import app.SimContext;
import app.core.actions.Action;
import app.core.actions.RescheduleEvent;

import java.util.ArrayList;
import java.util.List;

public class BaseEvent implements Event, Comparable<Event> {
    private double time;
    private List<Action> actionList;
    SimContext context;

    public BaseEvent(double time, SimContext context) {
        this.time = time;
        this.context = context;
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

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public void setInterval(double interval) {
            addListener(new RescheduleEvent(this,interval));
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

    public void addListener(Action l) {
        actionList.add(l);
    }


}
