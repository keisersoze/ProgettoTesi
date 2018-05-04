package app.utils;

import app.H20Sim;
import app.core.h20.actions.logic.HandleTransmission;
import app.factory.ActionTypes;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyLib {
    public static double map(double value, double low1, double high1, double low2, double high2) {
        return low2 + (high2 - low2) * (value - low1) / (high1 - low1);
    }

    public static float random(float min, float max) {
        return min + (int) (new MersenneTwister().nextDouble() * ((max - min) + 1));
    }

    public static List<Sensor> calculateNeighbors(Sensor sensor, SimContext context) {
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

    /**
     * @param sender
     * @param receiver
     * @param context
     * @return mW
     */
    public static double calculateNoise(Sensor sender, Sensor receiver, SimContext context) {
        double acc = 0;
        HandleTransmission action = (HandleTransmission)context.getCoreFactory().getAction(ActionTypes.HandleTransmission);
        Map<Sensor, Double> map = action.getSensorTransmissionMap();
        for (Sensor s : context.getSensors()) {
            if (s.isTransmitting() && !s.equals(sender)) {
                double distance = receiver.getEuclideanDistance(s);
                if (context.getSimTime() - map.get(s) * H20Sim.SOUND_SPEED >= distance) // se il segnale di disturbo è arrivato
                    acc += Math.pow(10, powerReceived(receiver.getEuclideanDistance(s)) / 10);
            }
        }
        return acc;
    }

    /**
     * @param receiver
     * @param context
     * @return mW
     */
    public static double calculateNoise(Sensor receiver, SimContext context) {
        double acc = 0;
        for (Sensor s : context.getSensors()) {
            if (s.isTransmitting()) {
                acc += Math.pow(10, powerReceived(receiver.getEuclideanDistance(s)) / 10);
            }
        }
        return acc;
    }

    /**
     * @param distance
     * @return dB
     */
    public static double powerReceived(double distance) {
        return H20Sim.SENSOR_POWER - (Math.pow(distance / 1000, H20Sim.K) * Math.pow(Math.pow(10, throp(H20Sim.SENSOR_FREQUENCY) / 10), distance / 1000));
    }

    /**
     * @param f
     * @return db/km
     */
    private static double throp(double f) {
        f = f / 1000;
        return (0.11 * Math.pow(f, 2)) / (1 + Math.pow(f, 2)) + (44 * Math.pow(f, 2)) / (4100 + Math.pow(f, 2)) + 2.75 * Math.pow(10, -4) * Math.pow(f, 2) + 0.003;
    }

    public static double todBm(double x) {
        return 10 * Math.log10(x);
    }

    public static double tomW(double x) {
        return Math.pow(10, x / 10);
    }


    public static boolean deterministicProtocol(Transmission transmission, SimContext context) {
        if (transmission.getHop() > 5) {
            return false;
        }
        if (H20Sim.PROTOCOL.equals("Deterministic") || H20Sim.PROTOCOL.equals("Combined")) {
            return transmission.getSender().getY() + context.getThreeshold() < transmission.getReceiver().getY();
        }
        return true;
    }


    public static boolean probabilisticProtocol(SimContext context) {
        if (H20Sim.PROTOCOL.equals("Probabilistic")) {
            return context.getMarsenneTwister().nextDouble() < 0.3;
        } else if (H20Sim.PROTOCOL.equals("Combined")) {
            return context.getMarsenneTwister().nextDouble() < 0.7;
        }
        return true;
    }
}
