package app.model.jme3.impl;

import app.model.Sensor;
import app.model.impl.AbstractTransmission;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;

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
