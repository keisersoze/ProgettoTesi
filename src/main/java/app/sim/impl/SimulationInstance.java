package app.sim.impl;

import app.H2OSim;
import app.core.events.Event;
import app.core.events.impl.MoveEvent;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.factory.EventTypes;
import app.sim.impl.AbstractSimIstance;
import app.stats.Collector;

public class SimulationInstance extends AbstractSimIstance implements Runnable {


    public SimulationInstance(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
    }

    public void run() {


        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreComponentsFactory().getEvent(EventTypes.StatisticEvent,0, this);
        Event move_evt = getCoreComponentsFactory().getEvent(EventTypes.MoveEvent,0, this);

        //imposto gli eventi periodici
        stats_evt.setInterval(10);
        move_evt.setInterval(10);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(stats_evt);
        getScheduler().addEvent(move_evt);

        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();

        }

        System.out.println(getSimTime());
    }


}
