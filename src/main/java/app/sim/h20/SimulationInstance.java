package app.sim.h20;

import app.H20Sim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.h20.MyCoreFactory;
import app.model.Sensor;
import app.stats.Collector;
import app.utils.MyLib;
import app.utils.Settings;

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

        super.initEvents();

        while (getPercentageCompleted() < 100 && !H20Sim.STOPPED) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            Settings.updateProgressBar(getPercentageCompleted());
        }
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }
}
