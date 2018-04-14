package app;

import org.apache.commons.math3.random.MersenneTwister;

public class MyLib {
    public static double map (double value, double low1, double high1, double low2, double high2) {
        return low2 + (high2 - low2) * (value - low1) / (high1 - low1);
    }

    public static float random (float min, float max) {
        return min + (int) (new MersenneTwister().nextDouble() * ((max - min) + 1));
    }
}
