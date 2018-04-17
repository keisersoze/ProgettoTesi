package app.sim.h20;

import app.H2OSim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.ModelFactory;
import app.factory.h20.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.factory.h20.MyModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.sim.MyLib;
import app.stats.Collector;

import java.util.ArrayList;
import java.util.List;

public class SimulationInstance extends AbstractSimIstance implements Runnable {
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelFactory modelFactory;
    private final CoreFactory coreFactory;


    public SimulationInstance (Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelFactory = new MyModelFactory();
        coreFactory = new MyCoreFactory();

    }

    public void run () {

        Sensor s1 = modelFactory.getSensor(0, 100, 0);
        sensors.add(s1);

        Sensor s2 = modelFactory.getSensor(0, 50, 0);
        sensors.add(s2);

        Sensor s3 = modelFactory.getSensor(0, 0, 0);
        sensors.add(s3);

        sensors.get(0).setSink(true);

        for (Sensor sensor : getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
        }
        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);
        Event move_evt = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);
        Event arrival_evt = getCoreFactory().getEvent(EventTypes.ArrivalEvent, 0, this);

        //imposto gli eventi periodici
        stats_evt.setInterval(0);
        move_evt.setInterval(10);


        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(arrival_evt);

        //avvio la simulazione

        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();

        }

        System.out.println(getSimTime());
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }

    @Override
    public List<Sensor> getSensors () {
        return sensors;
    }

    @Override
    public List<Frame> getFrames () {
        return frames;
    }

    @Override
    public void addFrame (Frame frame) {
        frames.add(frame);
    }

    public void removeFrame (Frame f) {
        frames.remove(f);
    }

    @Override
    public ModelFactory getModelFactory () {
        return modelFactory;
    }
}
