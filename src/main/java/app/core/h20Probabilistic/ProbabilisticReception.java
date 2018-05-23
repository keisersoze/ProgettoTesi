package app.core.h20Probabilistic;

import app.core.Action;
import app.core.Event;
import app.model.Transmission;
import app.sim.SimContext;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ProbabilisticReception implements Action {
    private static double N0 = 0.0016;
    private static double af = 1.1870;
    private static double s = 2;

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();

        double distance = transmission.getSender().getEuclideanDistance(transmission.getReceiver());
        distance /= 1000;
        transmission.setSuccessfull(correctTransmission(distance, context));
    }

    private static double calculateGamma (double distance) {
        return 8 / (pow(distance, s) * pow(af, distance));
    }

    private static double calculateQe (double gamma) {
        return (1.0 / 2.0) * (1 - sqrt(gamma / (1 + gamma)));
    }

    private static boolean correctTransmission (double distance, SimContext context) {
        double gamma = calculateGamma(distance);
        double Qe = calculateQe(gamma);
        return context.getMarsenneTwister().nextDouble() < pow(1 - Qe, 320);          //320 sono i bit del messaggio
    }
}
