package app.model.impl;

import app.model.Frame;
import app.model.Sensor;
import app.model.Trasmission;

public class BaseTrasmission implements Trasmission {
    private Sensor sender;
    private Sensor receiver;
    private double snr;
    private boolean isSuccessfull;

    public BaseTrasmission(Sensor sender, Sensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
        isSuccessfull = true;
    }


    @Override
    public void updateSnr(double x) {

    }

    @Override
    public boolean isSuccessfull() {
        return isSuccessfull;
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
