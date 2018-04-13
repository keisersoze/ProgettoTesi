package app.core.h20.events;

import app.core.Action;
import app.core.Event;
import app.core.h20.actions.utility.Reschedule;
import app.model.Frame;
import app.sim.SimContext;

import java.util.ArrayList;
import java.util.List;

public class BaseEvent implements Event, Comparable<Event> {
    SimContext context;
    private double time;
    private List<Action> actionList;

    public BaseEvent(double time, SimContext context) {
        this.context = context;
        this.time = context.getSimTime() + time;
        actionList = new ArrayList<>();
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


    //getters

    public double getTime() {
        return time;
    }

    @Override
    public SimContext getContext() {
        return context;
    }

    /**
     * @return NULL
     */
    @Override
    public Frame getFrame() {
        return null;
    }


    /**
     * @param time
     */
    public void updateTime(double time) {
        this.time = context.getSimTime() + time;
    }

    /**
     * @param interval
     */
    @Override
    public void setInterval(double interval) {
        addAction(new Reschedule(interval));
    }

    /**
     *
     */
    public Event tick() {
        for (Action l : actionList) {
            l.execute(this);
        }
        return this;
    }

    /**
     * @param action
     */
    public void addAction(Action action) {
        actionList.add(action);
    }


}
