package app.sim.h20;

import app.H20Sim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.h20.MyCoreFactory;
import app.factory.h20Reliable.h20RCoreFactory;
import app.model.Sensor;
import app.stats.Collector;
import app.utils.MyLib;
import app.utils.Settings;

public class SimulationInstance extends AbstractSimInstance {
    private final CoreFactory coreFactory;


    public SimulationInstance (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler);
        setName(instanceName);
        if (true) {
            coreFactory = new MyCoreFactory();
        } else {
            coreFactory = new h20RCoreFactory();
        }
    }

    public void run () {

        getSensors().addAll(getModelFactory().deploySensors(H20Sim.DEPLOYMENT_TYPE));
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
        System.out.println(getSimTime());
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }

}
