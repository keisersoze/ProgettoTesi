package app.model.h20;

import app.model.Sensor;
import app.model.Transmission;

public abstract class AbstractTransmission{
    private double snr;
    private boolean isSuccessfull;


    public AbstractTransmission() {
        isSuccessfull = true;
    }

    public void updateSnr(double x) { }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean stato) {
        isSuccessfull = stato;
    }

    public double getSnr() {
        return snr;
    }

}
