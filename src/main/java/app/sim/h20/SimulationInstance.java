package app.sim.h20;

import app.H2OSim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.h20.EventTypes;
import app.factory.ModelFactory;
import app.factory.h20.MyModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;

import java.util.ArrayList;
import java.util.List;

public class SimulationInstance extends AbstractSimIstance implements Runnable {
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelFactory modelFactory;


    public SimulationInstance(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelFactory = new MyModelFactory();
    }

    public void run() {


        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);
        Event move_evt = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);

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

    @Override
    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public void removeFrame(Frame f) {
        frames.remove(f);
    }

    @Override
    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
