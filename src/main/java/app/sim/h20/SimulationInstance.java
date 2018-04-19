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

import java.util.*;

public class SimulationInstance extends AbstractSimIstance implements Runnable {
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelFactory modelFactory;
    private final CoreFactory coreFactory;


    public SimulationInstance(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelFactory = new MyModelFactory();
        coreFactory = new MyCoreFactory();

    }

    public void run() {

        for (int i = 0; i < 1000; i++) {
            Sensor s1 = modelFactory.getSensor(MyLib.random(0, 200), MyLib.random(0, 80), MyLib.random(0, 200));
            sensors.add(s1);
        }

        Sensor s1 = modelFactory.getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s1);

        Sensor s2 = modelFactory.getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s2);

        Sensor s3 = modelFactory.getSensor(MyLib.random(0, 200), 90, MyLib.random(0, 200));
        sensors.add(s3);

        s1.setSink(true);
        s2.setSink(true);
        s3.setSink(true);

        for (Sensor sensor : getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
        }
        // creo l'evento che richiama la funzionalità di campionamento per le statistiche

        Event move_evt = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);
        Event arrival_evt = getCoreFactory().getEvent(EventTypes.ArrivalEvent, 0, this);
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);



        //imposto gli eventi periodici
        move_evt.setInterval(10);
        stats_evt.setInterval(1/H2OSim.LAMDA);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(arrival_evt);
        getScheduler().addEvent(stats_evt);
        //avvio la simulazione

        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
        }

        System.out.println(getSimTime());

        int cont = 0;
        for (LinkedList<Double> list : getFramesArrived().values()) {
            if(list.size()>0)
                cont ++ ;


        }
        System.out.println(frames.size());
        System.out.println(cont);
    }

    @Override
    public CoreFactory getCoreFactory() {
        return coreFactory;
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
    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
