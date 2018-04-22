package app.sim.h20;

import app.Canvas;
import app.H20Sim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.EventTypes;
import app.factory.h20.GraphicCoreFactory;
import app.model.Sensor;
import app.sim.MyLib;
import app.stats.Collector;
import com.jme3.system.AppSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimIstance implements Runnable {
    public static int nanos;
    public static long millis = 1;
    private static Canvas canvas;
    private final CoreFactory coreFactory;

    public GraphicSim (Collector collector, Scheduler scheduler) {
        super(collector, scheduler);
        canvas = new Canvas(this);
        coreFactory = new GraphicCoreFactory(canvas);
    }

    private static void setSettings () {
        AppSettings settings = new AppSettings(true);

        settings.setTitle("Underwater wireless sensors networks");
        settings.setFrameRate(60);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int i = 0; // note: there are usually several, let's pick the first
        settings.setResolution(1400, 900);
        settings.setFrequency(modes[i].getRefreshRate());
        settings.setBitsPerPixel(modes[i].getBitDepth());
        settings.setFullscreen(false);
        settings.setSamples(16);
        settings.setGammaCorrection(true);
        settings.setSettingsDialogImage("Interface/unive.jpg");

        try {
            settings.setIcons(new BufferedImage[]{ImageIO.read(new File("assets/Interface/unive_red.jpg"))});
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        Event stats_evt = getCoreFactory().getEvent(EventTypes.StatisticEvent, 0, this);
        //imposto gli eventi periodici
        stats_evt.setInterval(1 / H20Sim.LAMDA);

        //aggiungo gli eventi periodici allo scheduler

        getScheduler().addEvent(arrival_evt);
        getScheduler().addEvent(stats_evt);


        //avvio la simulazione

        while (getPercentageCompleted() < 100 && !H20Sim.STOPPED) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            try {
                Thread.sleep(millis, nanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CoreFactory getCoreFactory () {
        return coreFactory;
    }
}
