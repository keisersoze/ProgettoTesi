package app;

import app.core.events.Event;
import app.core.events.impl.ArrivalEvent;
import app.core.events.impl.MoveEvent;
import app.core.events.impl.StatisticsEvent;
import app.core.scheduler.Scheduler;
import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;
import app.model.impl.V3FSensor;
import app.stats.Collector;
import com.jme3.math.ColorRGBA;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

public class GraphicSim extends AbstractSimIstance {


    public GraphicSim(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
    }

    public void run() {

        Canvas canvas = new Canvas();
        V3FSensor s1, s2, s3 ,s4;

        canvas.start();

        while (!canvas.isCharged()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        s1 = new V3FSensor(0, 0, 0, canvas);
        s2 = new V3FSensor(4, 0, 0, canvas);
        s3 = new V3FSensor(0, 0, 4, canvas);
        s4 = new V3FSensor(4, 0, 4, canvas);



        //inizializzazione
        getSensors().add(s1);
        getSensors().add(s2);
        getSensors().add(s3);
        getSensors().add(s4);

        s1.setSink(true);

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = new StatisticsEvent(0, this);
        Event move_evt = new MoveEvent(0, this);
        Event arrival_evt = new ArrivalEvent(0,this);

        //imposto gli eventi periodici
        stats_evt.setInterval(50);
        move_evt.setInterval(100);

        //aggiungo gli eventi periodici allo scheduler
        getScheduler().addEvent(arrival_evt);


        //avvio la simulazione
        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled= getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            for (Frame frame: getFrames()) {
                Trasmission t = frame.getCurrentTransmission();
                if(t != null){
                    canvas.linkBetweenGeometries(t.getReceiver().getGeometry(),t.getSender().getGeometry(), ColorRGBA.Green);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(getSimTime() + " " + getSensors().get(0).getPosition());
    }




}
