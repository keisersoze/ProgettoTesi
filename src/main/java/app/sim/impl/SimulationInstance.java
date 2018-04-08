package app.sim.impl;

import app.H2OSim;
import app.core.Event;
import app.core.Scheduler;
import app.core.EventTypes;
import app.model.ModelComponentsFactory;
import app.model.h20.MyModelComponentsFactory;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;

import java.util.ArrayList;
import java.util.List;

public class SimulationInstance extends AbstractSimIstance implements Runnable {
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelComponentsFactory modelComponentsFactory;


    public SimulationInstance(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelComponentsFactory = new MyModelComponentsFactory();
    }

    public void run() {


        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreComponentsFactory().getEvent(EventTypes.StatisticEvent,0, this);
        Event move_evt = getCoreComponentsFactory().getEvent(EventTypes.MoveEvent,0, this);

        //imposto gli eventi periodici
        stats_evt.setInterval(0);
        move_evt.setInterval(10);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(stats_evt);
        getScheduler().addEvent(move_evt);

        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();

        }

        System.out.println(getSimTime());
    }

    @Override
    public List<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public List<Frame> getFrames() {
        return frames;
    }

    public void removeFrame(Frame f){
        frames.remove(f);
    }

    @Override
    public ModelComponentsFactory getModelComponentsFactory() {
        return modelComponentsFactory;
    }
}
