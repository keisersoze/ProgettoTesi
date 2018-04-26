package app.utils;

import app.H20Sim;
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
            if (powerReceived(sensor.getEuclideanDistance(sensor1)) > H20Sim.SENSIBILITY) {
                if (sensor != sensor1) {
                    myNeighbors.add(sensor1);
                }
            }
        }
        return myNeighbors;
    }

    public static double calculateNoise (Sensor sender, Sensor receiver, SimContext context, double time) {
        double acc = 0;
        for (Sensor s : context.getSensors()) {
            if (s.isTransmitting() && !s.equals(sender) && (receiver.getEuclideanDistance(s) >= H20Sim.SOUND_SPEED * (context.getSimTime() - time))) {
                acc += Math.pow(10, powerReceived(receiver.getEuclideanDistance(s)) / 10);
            }
        }
        return acc;
    }

    //Modelli path loss
    public static double powerReceived (double distance) {
        return H20Sim.SENSOR_POWER - (Math.pow(distance / 1000, H20Sim.K) * Math.pow(Math.pow(10, throp(H20Sim.SENSOR_FREQUENCY) / 10), distance / 1000));
    }

    private static double throp (double f) {
        f = f / 1000;
        return (0.11 * Math.pow(f, 2)) / (1 + Math.pow(f, 2)) + (44 * Math.pow(f, 2)) / (4100 + Math.pow(f, 2)) + 2.75 * Math.pow(10, -4) * Math.pow(f, 2) + 0.003;
    }


}
