package app.model.h20;

import app.model.Sensor;
import app.model.Transmission;

public abstract class AbstractTransmission implements Transmission {
    private Sensor sender;
    private Sensor receiver;
    private double snr;
    private boolean isSuccessfull;


    public AbstractTransmission (Sensor sender, Sensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
        isSuccessfull = true;
    }

    @Override
    public void updateSnr (double x) {

    }

    @Override
    public boolean isSuccessfull () {
        return isSuccessfull;
    }

    public void setSuccessfull (boolean stato) {
        isSuccessfull = stato;
    }

    public Sensor getSender () {
        return sender;
    }

    public Sensor getReceiver () {
        return receiver;
    }

    public double getSnr () {
        return snr;
    }

}
