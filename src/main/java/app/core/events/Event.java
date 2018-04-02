package app.core.events;

import app.sim.SimContext;
import app.core.actions.Action;

public interface Event {

    void addAction(Action action);

    /**
     * chiama tutte le callback associate all'evento
     */
    Event tick();

    /**
     * restituisce un valore che indica la priorità dell'evento
     *
     * @return il valore che indica la priorità
     */
    double getTime();

    /**
     *
     */
    void setInterval(double interval);


    /**
     *
     */
    void updateTime(double time);

    /**
     * @return
     */
    SimContext getContext();

}

