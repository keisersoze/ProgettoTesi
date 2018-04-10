package app.sim.impl;

import app.Canvas;
import app.H2OSim;
import app.core.events.Event;
import app.core.scheduler.Scheduler;
import app.factory.EventTypes;
import app.factory.ModelFactory;
import app.factory.jme3.GraphicModelFactory;
import app.factory.jme3.impl.JmeModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.jme3.GraphicFrame;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;
import app.stats.Collector;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimIstance {
    private static Canvas canvas;
    private final List<GraphicSensor> sensors;
    private final List<GraphicFrame> frames;
    private final GraphicModelFactory modelFactory;

    public GraphicSim(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        canvas = new Canvas();
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelFactory = new JmeModelFactory();
    }

    private static void setSettings() {
        AppSettings settings = new AppSettings(true);

        settings.setTitle("Underwater wireless sensors networks");
        settings.setFrameRate(60);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int i = 0; // note: there are usually several, let's pick the first
        settings.setResolution(modes[i].getWidth(), modes[i].getHeight());
        settings.setFrequency(modes[i].getRefreshRate());
        settings.setBitsPerPixel(modes[i].getBitDepth());
        settings.setFullscreen(device.isFullScreenSupported());

        settings.setSamples(16);
        settings.setGammaCorrection(true);
        settings.setSettingsDialogImage("Interface/unive.jpg");

        canvas.setShowSettings(true);
        canvas.setDisplayStatView(false);
        canvas.setSettings(settings);
    }

    public void run() {
        List<GraphicFrame> listCompleted = new ArrayList<>();

        setSettings();

        canvas.start();

        while (!canvas.isCharged()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //inizializzazione
        for (int i = 0; i < 50; i++) {
            GraphicSensor temp = modelFactory.getSensor(canvas.random(-100, 100), canvas.random(-30, 30), canvas.random(-100, 100));
            temp.draw(canvas);
            sensors.add(temp);
        }
        sensors.get(0).setSink(true);

        // creo l'evento che richiama la funzionalità di campionamento per le statistiche
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);
        Event move_evt = getCoreFactory().getEvent(EventTypes.MoveEvent, 0, this);
        Event arrival_evt = getCoreFactory().getEvent(EventTypes.ArrivalEvent, 0, this);

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

                for (GraphicFrame frame : frames) {
                    if (!frame.isArrived()) {
                        GraphicTransmission current = frame.getCurrentTransmission();
                        if (current != null) {
                            canvas.enqueue((Callable<Spatial>) () -> canvas.linkTransmission(current, ColorRGBA.Green)).get();
                            //canvas.enqueue((Callable<Spatial>) () -> canvas.fadeTransmission(frame)).get();
                        }
                    } else if (frame.getTransmissionHistory().size() > 0) {
                        canvas.enqueue((Callable<Spatial>) () -> canvas.deleteLinkTransmission(frame)).get();
                        listCompleted.add(frame);
                    }
                }
                canvas.enqueue((Callable<Spatial>) () -> canvas.updatePositions(sensors)).get();
                getFrames().removeAll(listCompleted);
                Thread.sleep(10);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(getSimTime() + " " + getSensors().get(0).getPosition());
    }

    @Override
    public List<? extends Sensor> getSensors() {
        return sensors;
    }

    @Override
    public List<? extends Frame> getFrames() {
        return frames;
    }

    @Override
    public void addFrame(Frame frame) {
        frames.add((GraphicFrame) frame);
    }

    @Override
    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
