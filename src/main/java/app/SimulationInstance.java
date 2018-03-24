package app;

import app.evt.StatisticsEvent;
import app.stats.SamplesCollector;
import app.evt.Event;
import app.evt.actions.UpdateStats;
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


    public void run() {
        //inizializzazione

        Event stats_evt = new StatisticsEvent(0);
        stats_evt.addListener(new UpdateStats(this));
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
