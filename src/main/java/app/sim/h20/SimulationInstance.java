package app.sim.h20;

import app.H20Sim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.model.Sensor;
import app.sim.MyLib;
import app.stats.Collector;

import java.util.LinkedList;

public class SimulationInstance extends AbstractSimIstance implements Runnable {
    private final CoreFactory coreFactory;


    public SimulationInstance (Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        coreFactory = new MyCoreFactory();
    }

    public void run () {

        for (Sensor sensor : getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
        }
        // creo l'evento che richiama la funzionalità di campionamento per le statistiche

        Event move_evt = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);
        Event arrival_evt = getCoreFactory().getEvent(EventTypes.ArrivalEvent, 0, this);
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);


        //imposto gli eventi periodici
        move_evt.setInterval(10);
        stats_evt.setInterval(1 / H20Sim.LAMDA);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(arrival_evt);
        getScheduler().addEvent(stats_evt);
        //avvio la simulazione

        for (double i = 0; i < H20Sim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            System.out.println((i / H20Sim.NEVENTS) * 100 + "% Thread:" + Thread.currentThread().getName());
        }

        System.out.println(getSimTime());

        int cont = 0;
        for (LinkedList<Double> list : getFramesArrived().values()) {
            if (list.size() > 0) { cont++; }


        }
        System.out.println(getFrames().size());
        System.out.println(cont);
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }


}
