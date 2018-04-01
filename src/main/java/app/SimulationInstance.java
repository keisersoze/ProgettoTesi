package app;

import app.core.events.Event;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.model.Sensor;
import app.stats.Collector;

import java.util.List;

public class SimulationInstance implements Runnable, SimContext {

    private final Scheduler scheduler;
    private final Collector collector;
    private final List<Sensor> sensors;
    private double sim_time;

    public SimulationInstance(Collector collector, Scheduler scheduler) {
        this.collector = collector;
        this.scheduler = scheduler;
        sim_time = 0.0;
        sensors = null;
    }


    public void run() {

        //inizializzazione

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = new StatisticsEvent(0, this);

        //imposto gli eventi periodici
        stats_evt.setInterval(50);

        //aggiungo gli eventi periodici allo scheduler
        scheduler.addEvent(stats_evt);

        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            sim_time = scheduler.scheduleEvent().tick().getTime();
        }
        System.out.println(sim_time);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public double getSim_time() {
        return sim_time;
    }

    @Override
    public List<Sensor> getSensors() {
        return null;
    }

    public Collector getCollector() {
        return collector;
    }
}
