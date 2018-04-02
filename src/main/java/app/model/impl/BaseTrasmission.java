package app.model.impl;

import app.model.Sensor;
import app.model.Trasmission;

public class BaseTrasmission implements Trasmission {
    private Sensor sender;
    private Sensor receiver;
    private double snr;
    private boolean isSuccessfull;
    private boolean terminated;

    public BaseTrasmission(Sensor sender, Sensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
        isSuccessfull = true;
        terminated = false;
    }


    @Override
    public void updateSnr(double x) {

    }

    @Override
    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean stato) {
        isSuccessfull = stato;
    }

    public Sensor getSender() {
        return sender;
    }

    public Sensor getReceiver() {
        return receiver;
    }

    public double getSnr() {
        return snr;
    }
}
