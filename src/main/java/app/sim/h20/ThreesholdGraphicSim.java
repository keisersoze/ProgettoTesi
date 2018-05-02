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

public class ThreesholdGraphicSim extends AbstractSimIstance{
    private final CoreFactory coreFactory;
    private static double THREESHOLD_MAX = 1000 ;

    public ThreesholdGraphicSim (Collector collector, Scheduler scheduler) {
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
        System.out.println(getSimTime());
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }

    @Override
    public void setPercentageCompleted() {
        super.setPercentageCompleted();
        if (super.getPercentageCompleted()%10 == 0) //TODO attenzione
            H20Sim.THRESHOLD+=THREESHOLD_MAX*0.1;
    }
}
