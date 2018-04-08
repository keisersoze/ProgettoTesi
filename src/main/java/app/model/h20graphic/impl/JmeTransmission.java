package app.model.h20graphic.impl;

import app.model.Sensor;
import app.model.h20.AbstractTransmission;
import app.model.h20graphic.GraphicSensor;
import app.model.h20graphic.GraphicTransmission;

public class JmeTransmission extends AbstractTransmission implements GraphicTransmission {

    private boolean terminated;

    public JmeTransmission(Sensor sender, Sensor receiver) {
        super(sender, receiver);
        this.terminated = false;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public GraphicSensor getGraphicSender() throws ClassCastException {
        return (GraphicSensor) getSender();
    }

    @Override
    public GraphicSensor getGraphicReceiver() throws ClassCastException {
        return (GraphicSensor) getReceiver();
    }


}
