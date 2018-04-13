package app.sim.h20;

import app.Canvas;
import app.H2OSim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.ModelFactory;
import app.factory.h20.EventTypes;
import app.factory.h20.MyModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.stats.Collector;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimIstance {
    private static Canvas canvas;
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelFactory modelFactory;

    public GraphicSim(Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        canvas = new Canvas();
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        modelFactory = new MyModelFactory();
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
        List<Frame> listCompleted = new ArrayList<>();

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
        for (int i = 0; i < 100; i++) {
            Sensor temp = modelFactory.getSensor(canvas.random(-100, 100), canvas.random(-30, 30), canvas.random(-100, 100));
            sensors.add(temp);
        }

        sensors.get(0).setSink(true);

        try {
            canvas.enqueue(() -> canvas.drawSensors(sensors)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

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

                for (Frame frame : frames) {
                    if (!frame.isArrived()) {
                        Transmission current = frame.getCurrentTransmission();
                        if (current != null) {
                            canvas.enqueue(() -> canvas.linkTransmission(frame, ColorRGBA.Green)).get();
                        }
                    } else if (frame.getTransmissionHistory().size() > 0) {
                        canvas.enqueue(() -> canvas.deleteLinkTransmission(frame)).get();
                        listCompleted.add(frame);
                    }
                }
                canvas.enqueue(() -> canvas.updateSensorsPositions()).get();
                getFrames().removeAll(listCompleted);
                Thread.sleep(0);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(getSimTime() + " " + getSensors().get(0).getPosition());
    }

    @Override
    public List<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public List<Frame> getFrames() {
        return frames;
    }

    @Override
    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    @Override
    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
