package app.model.h20G.impl;

import app.model.Sensor;
import app.model.h20.AbstractTransmission;
import app.model.h20G.GraphicSensor;
import app.model.h20G.GraphicTransmission;

public class JmeTransmission extends AbstractTransmission implements GraphicTransmission {
    private GraphicSensor sender;
    private GraphicSensor receiver;
    private boolean terminated;

    public JmeTransmission(GraphicSensor sender, GraphicSensor receiver) {
        super();
        this.terminated = false;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public GraphicSensor getSender() throws ClassCastException {
        return sender;
    }

    @Override
    public GraphicSensor getReceiver() throws ClassCastException {
        return receiver;
    }


}
