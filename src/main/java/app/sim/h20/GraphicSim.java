package app.sim.h20;

import app.Canvas;
import app.H2OSim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.ModelFactory;
import app.factory.h20.EventTypes;
import app.factory.h20.GraphicCoreFactory;
import app.factory.h20.MyModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.sim.MyLib;
import app.stats.Collector;
import com.jme3.system.AppSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimIstance {
    private static Canvas canvas;
    public static long speed;
    public final List<Frame> frames;
    private final List<Sensor> sensors;
    private final ModelFactory modelFactory;
    private final CoreFactory coreFactory;

    public GraphicSim (Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        canvas = new Canvas(this);
        sensors = new ArrayList<>();
        frames = new CopyOnWriteArrayList<>();
        modelFactory = new MyModelFactory();
        coreFactory = new GraphicCoreFactory(canvas);
    }

    private static void setSettings () {
        AppSettings settings = new AppSettings(true);

        settings.setTitle("Underwater wireless sensors networks");
        settings.setFrameRate(60);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int i = 0; // note: there are usually several, let's pick the first
        settings.setResolution(1024, 700);
        settings.setFrequency(modes[i].getRefreshRate());
        settings.setBitsPerPixel(modes[i].getBitDepth());
        settings.setFullscreen(false);

        settings.setSamples(16);
        settings.setGammaCorrection(true);
        settings.setSettingsDialogImage("Interface/unive.jpg");

        canvas.setShowSettings(false);
        canvas.setDisplayStatView(false);
        canvas.setSettings(settings);
    }

    public void run () {

        setSettings();

        canvas.start();

        while (!canvas.isCharged()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = modelFactory.getSensor(MyLib.random(0, 200), MyLib.random(0, 80), MyLib.random(0, 200));
            sensors.add(s1);
        }

        Sensor s1 = modelFactory.getSensor(MyLib.random(0, 200), 100, MyLib.random(0, 200));
        sensors.add(s1);

        Sensor s2 = modelFactory.getSensor(MyLib.random(0, 200), 100, MyLib.random(0, 200));
        sensors.add(s2);

        s1.setSink(true);
        s2.setSink(true);

        for (Sensor sensor : getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
        }

        try {
            canvas.enqueue(() -> canvas.drawSensors(getSensors())).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche

        Event arrival_evt = getCoreFactory().getEvent(EventTypes.ArrivalEvent, 0, this);
        Event dummy = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);

        //imposto gli eventi periodici
        dummy.setInterval(0.001);

        //aggiungo gli eventi periodici allo scheduler

        getScheduler().addEvent(arrival_evt);
        //getScheduler().addEvent(dummy);


        //avvio la simulazione

        for (int i = 0; i < H2OSim.NEVENTS; i++) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(getSimTime() + " " + getSensors().get(0).getPosition());
    }

    @Override
    public List<Sensor> getSensors () {
        return sensors;
    }

    @Override
    public List<Frame> getFrames () {
        return frames;
    }

    @Override
    public void addFrame (Frame frame) {
        frames.add(frame);
    }

    @Override
    public ModelFactory getModelFactory () {
        return modelFactory;
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }
}
