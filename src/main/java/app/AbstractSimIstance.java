package app;

import app.core.events.Event;
import app.core.events.impl.ArrivalEvent;
import app.core.events.impl.MoveEvent;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;
import app.model.impl.V3FSensor;
import app.stats.Collector;
import com.jme3.math.ColorRGBA;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSimIstance implements SimContext{
    private final Scheduler scheduler;
    private final Collector collector;
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private double simTime;


    //Dati statistici che vengono utilizzati dal collector
    private int nframes; // numero di frame arrivati ai sink

    public AbstractSimIstance(Collector collector, Scheduler scheduler) {

        this.collector = collector;
        this.scheduler = scheduler;
        simTime = 0.0;
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        nframes = 0;
    }




    public Scheduler getScheduler() {
        return scheduler;
    }

    public double getSimTime() {
        return simTime;
    }

    @Override
    public List<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public List<Frame> getFrames() {
        return frames;
    }

    public Collector getCollector() {
        return collector;
    }


    // metodi per l'update dei dati statistici

    @Override
    public void frameArrived() {
        nframes++;
    }


    @Override
    public void setSimTime(double simTime) {
        this.simTime = simTime;
    }
}
