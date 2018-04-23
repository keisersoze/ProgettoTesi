package app.model.h20;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

public abstract class AbstractTransmission implements Transmission {
    private Sensor sender;
    private Sensor receiver;
    private double snr;
    private boolean isSuccessfull;
    private Frame frame;
    private int hop;
    private double time;
    private boolean arrived;

    AbstractTransmission (Sensor sender, Sensor receiver, Frame frame, int hop) {
        this.sender = sender;
        this.receiver = receiver;
        isSuccessfull = true;
        this.frame = frame;
        this.hop = hop;
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

    public Frame getFrame () {
        return frame;
    }

    @Override
    public int getHop () {
        return hop;
    }

    @Override
    public double getTime () {
        return time;
    }

    public void setTime (double time) {
        this.time = time;
    }

    @Override
    public boolean isArrived () {
        return arrived;
    }

    @Override
    public void setArrived (boolean arrived) {
        this.arrived = arrived;
    }
}
