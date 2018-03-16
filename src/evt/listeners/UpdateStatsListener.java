package evt.listeners;

import app.H2OSim;
import app.SimulationInstance;
import app.stats.StatsSample;
import evt.Event;
import evt.StatisticsEvent;

public class UpdateStatsListener implements Listener {
    private SimulationInstance simulation;
    int i;


    public UpdateStatsListener(SimulationInstance simulation) {
        this.simulation = simulation;
        i=0;
    }

    @Override
    public void execute() {
        simulation.getCollector().update(Thread.currentThread().getName(),new StatsSample(i));
        i++;

        //aggiunge il prossimo evento per la raccolta delle stats
        Event stats_evt = new StatisticsEvent(simulation.getSim_time()+ H2OSim.SAMPLING_INTERVAL);
        stats_evt.addListener(this);
        simulation.getScheduler().addEvent(stats_evt);

    }
}
