package app.evt.actions;

import app.H2OSim;
import app.SimulationInstance;
import app.evt.Event;
import app.evt.StatisticsEvent;
import app.stats.StatsSample;

public class UpdateStats implements Action {
    int i;
    private SimulationInstance simulation;


    public UpdateStats(SimulationInstance simulation) {
        this.simulation = simulation;
        i = 0;
    }

    @Override
    public void update() {
        simulation.getCollector().update(Thread.currentThread().getName(), new StatsSample(i));
        i++;

        //aggiunge il prossimo evento per la raccolta delle stats
        Event stats_evt = new StatisticsEvent(simulation.getSim_time() + H2OSim.SAMPLING_INTERVAL);
        stats_evt.addListener(this);
        simulation.getScheduler().addEvent(stats_evt);

    }
}
