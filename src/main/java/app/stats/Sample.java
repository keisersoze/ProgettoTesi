package app.stats;

import java.util.Map;

public interface Sample {
    double getSuccessfullRate ();

    double getGoodput ();

    double getAvgResponseTime ();

    Map<Integer, Double> getDeptArrivalSuccessRate ();

    double getTransmittingModeRate ();

    double getReceivingModeRate ();

    double getSleepModeRate ();
}
