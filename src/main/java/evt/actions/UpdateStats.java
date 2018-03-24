package evt.actions;

import app.H2OSim;
import app.SimulationInstance;
import app.stats.StatsSample;
import evt.Event;
import evt.StatisticsEvent;

public class UpdateStats implements Action {
    private SimulationInstance simulation;
    int i;


    public UpdateStats(SimulationInstance simulation) {
        this.simulation = simulation;
        i=0;
    }

    @Override
    public void update() {
        simulation.getCollector().update(Thread.currentThread().getName(),new StatsSample(i));
        i++;

        //aggiunge il prossimo evento per la raccolta delle stats
        Event stats_evt = new StatisticsEvent(simulation.getSim_time()+ H2OSim.SAMPLING_INTERVAL);
        stats_evt.addListener(this);
        simulation.getScheduler().addEvent(stats_evt);

    }
}
