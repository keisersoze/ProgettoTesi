package app.model.impl;

import app.model.Frame;

public class BaseFrame implements Frame {
    private BaseSensor sender;
    private BaseSensor receiver;
    private double snr;

    public BaseFrame(BaseSensor sender, BaseSensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }


    @Override
    public void updateSnr(double x) {

    }
}
