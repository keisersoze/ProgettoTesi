package app;

import app.model.Sensor;
import app.sim.SimContext;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;

public class MyLib {
    public static double map (double value, double low1, double high1, double low2, double high2) {
        return low2 + (high2 - low2) * (value - low1) / (high1 - low1);
    }

    public static float random (float min, float max) {
        return min + (int) (new MersenneTwister().nextDouble() * ((max - min) + 1));
    }

    public static List<Sensor> calculateNeighbors (Sensor sensor, SimContext context) {
        List<Sensor> myNeighbors = new ArrayList<>();
        for (Sensor sensor1 : context.getSensors()) {
            if (sensor.getEuclideanDistance(sensor1) < H2OSim.MAX_DISTANCE / H2OSim.SCALE){    //TODO: deve calcolarla
                myNeighbors.add(sensor1);
            }
        }
        return myNeighbors;
    }
}
