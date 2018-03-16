package app;

import app.stats.SamplesCollector;
import evt.Event;
import evt.StatisticsEvent;
import evt.listeners.UpdateStatsListener;
import app.scheduler.DefaultScheduler;
import app.scheduler.Scheduler;

public class SimulationInstance implements Runnable{
    private SamplesCollector collector;
    private double sim_time;
    Scheduler scheduler;
    public SimulationInstance(SamplesCollector s) {
        collector = s;
        scheduler = new DefaultScheduler();
        sim_time = 0.0;

    }

    @Override
    public void run() {
        //inizializzazione

        Event stats_evt = new StatisticsEvent(0);
        stats_evt.addListener(new UpdateStatsListener(this));
        scheduler.addEvent(stats_evt);

        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event event_scheduled = scheduler.scheduleEvent();
            sim_time = event_scheduled.getTime();
            event_scheduled.tick();
        }

        System.out.println(sim_time);

    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public double getSim_time() {
        return sim_time;
    }

    public SamplesCollector getCollector() {
        return collector;
    }
}
