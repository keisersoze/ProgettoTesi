package app.sim;

import app.H2OSim;
import app.model.Sensor;
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
            if (powerReceived(sensor.getEuclideanDistance(sensor1)) > H2OSim.SENSIBILITY) {
                if (sensor != sensor1) {
                    myNeighbors.add(sensor1);
                }
            }
        }
        return myNeighbors;
    }

    public static double calculateNoise (Sensor sensor, SimContext context) {
        double acc = 0;
        for (Sensor s : context.getSensors()) {
            if (s.isTransmitting())
                acc += powerReceived(s.getEuclideanDistance(sensor));
        }
        return acc;
    }

    public static double powerReceived (double distance) {
        return H2OSim.SENSOR_POWER - 20 * Math.log10(distance) + 20 * Math.log10(H2OSim.SENSOR_FREQUENCY) - 147.55;
    }
}
