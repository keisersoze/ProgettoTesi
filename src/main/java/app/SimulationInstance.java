package app;

import app.core.events.Event;
import app.core.events.impl.MoveEvent;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.model.Sensor;
import app.model.impl.BaseSensor;
import app.model.impl.V3FSensor;
import app.stats.Collector;

import java.util.ArrayList;
import java.util.List;

public class SimulationInstance implements Runnable, SimContext {

    private final Scheduler scheduler;
    private final Collector collector;
    private final List<Sensor> sensors;
    private double sim_time;

    //Dati statistici che vengono utilizzati dal collector
    private int nframes; // numero di frame arrivati ai sink

    public SimulationInstance(Collector collector, Scheduler scheduler) {

        this.collector = collector;
        this.scheduler = scheduler;
        sim_time = 0.0;
        sensors = new ArrayList<>();

        nframes = 0;
    }


    public void run() {

        Canvas canvas = new Canvas(null, null);
        V3FSensor s1, s2;

        canvas.start();

        while (!canvas.isCharged()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        s1 = new V3FSensor(0, 0, 0, canvas);
        s2 = new V3FSensor(0, 0, 0, canvas);

        //inizializzazione
        sensors.add(s1);
        sensors.add(s2);

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = new StatisticsEvent(0, this);
        Event move_evt = new MoveEvent(0, this);

        //imposto gli eventi periodici
        stats_evt.setInterval(50);
        move_evt.setInterval(100);

        //aggiungo gli eventi periodici allo scheduler
        scheduler.addEvent(stats_evt);
        scheduler.addEvent(move_evt);

        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            sim_time = scheduler.scheduleEvent().tick().getTime();
        }
        System.out.println(sim_time + " " + sensors.get(0).getPosition());
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public double getSimTime() {
        return sim_time;
    }

    @Override
    public List<Sensor> getSensors() {
        return sensors;
    }

    public Collector getCollector() {
        return collector;
    }


    // metodi per l'update dei dati statistici

    @Override
    public void frameArrived() {
        nframes++;
    }

}
