package app.sim.impl;

import app.Canvas;
import app.H2OSim;
import app.core.events.Event;
import app.core.events.impl.ArrivalEvent;
import app.core.events.impl.MoveEvent;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;
import app.model.impl.V3FSensor;
import app.stats.Collector;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimIstance {

    private Canvas canvas;

    public GraphicSim(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        canvas = new Canvas();
    }

    public void run() {
        List<Frame> listCompleted = new ArrayList<>();
        canvas.start();

        while (!canvas.isCharged()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //inizializzazione
        for (int i = 0; i < 400; i++){
            V3FSensor temp = new V3FSensor(canvas.random(-100, 100), canvas.random(-30, 30), canvas.random(-100, 100), canvas);
            getSensors().add(temp);
        }

        getSensors().get(0).setSink(true);

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreComponentsFactory().getEvent(EventTypes.StatisticEvent,0, this);
        Event move_evt = getCoreComponentsFactory().getEvent(EventTypes.MoveEvent,0, this);
        Event arrival_evt = getCoreComponentsFactory().getEvent(EventTypes.ArrivalEvent,0,this);

        //imposto gli eventi periodici
        stats_evt.setInterval(50);
        move_evt.setInterval(10);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(arrival_evt);
        //getScheduler().addEvent(move_evt);


        //avvio la simulazione
        try {
            for (int i = 0; i < H2OSim.NEVENTS; i++) {
                Event evt_scheduled = getScheduler().scheduleEvent();
                setSimTime(evt_scheduled.getTime());
                evt_scheduled.tick();

                for (Frame frame : getFrames()) {
                    if(!frame.isArrived()) {
                        Trasmission current = frame.getCurrentTransmission();
                        if (current != null) {
                            canvas.enqueue((Callable<Spatial>) () -> canvas.linkTransmission(current, ColorRGBA.Green)).get();
                            canvas.enqueue((Callable<Spatial>) () -> canvas.fadeTransmission(frame)).get();
                        }
                    } else if (frame.getTransmissionHistory().size() > 0){
                        canvas.enqueue((Callable<Spatial>) () -> canvas.deleteLinkTransmission(frame)).get();
                        listCompleted.add(frame);
                    }
                }
                canvas.enqueue((Callable<Spatial>) () -> canvas.updatePositions(getSensors())).get();
                getFrames().removeAll(listCompleted);
                Thread.sleep(100);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(getSimTime() + " " + getSensors().get(0).getPosition());
    }


}
