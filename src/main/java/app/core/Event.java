package app.core;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

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

    /**
     *
     */
    Transmission getTransmission();

    /**
     *
     */
    Frame getFrame();

    Sensor getSensor();

    int getInt();
}

