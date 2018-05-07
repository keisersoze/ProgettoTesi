package app.sim.h20G;

import app.H20Sim;
import app.core.Event;
import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.h20G.GraphicCoreFactory;
import app.model.Sensor;
import app.sim.h20.AbstractSimInstance;
import app.stats.Collector;
import app.utils.Canvas;
import app.utils.MyLib;
import app.utils.Settings;
import com.jme3.system.AppSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GraphicSim extends AbstractSimInstance {
    public static int nanos;
    public static long millis = 1;
    private static Canvas canvas;
    private final CoreFactory coreFactory;

    public GraphicSim (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler);
        canvas = new Canvas(this);
        coreFactory = new GraphicCoreFactory(canvas);
        setName(instanceName);
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

        getSensors().addAll(getModelFactory().deploySensors(H20Sim.DEPLOYMENT_TYPE));
        for (Sensor sensor : getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
        }

        try {
            canvas.enqueue(() -> canvas.drawSensors(getSensors())).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        super.initEvents();

        //avvio la simulazione

        while (getPercentageCompleted() < 100 && !H20Sim.STOPPED) {
            Event evt_scheduled = getScheduler().scheduleEvent();
            setSimTime(evt_scheduled.getTime());
            evt_scheduled.tick();
            Settings.updateProgressBar(getPercentageCompleted());
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


    @Override
    public double getThreeshold () {
        return H20Sim.THRESHOLD;
    }
}
