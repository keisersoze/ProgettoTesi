package app.model.jme3.impl;

import app.model.Sensor;
import app.model.Transmission;
import app.model.impl.AbstractTransmission;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;

public class JmeTransmission extends AbstractTransmission implements GraphicTransmission {

    private boolean terminated;

    public JmeTransmission (Sensor sender, Sensor receiver) {
        super(sender, receiver);
        this.terminated = false;
    }

    public JmeTransmission (Transmission currentTransmission) {
        super(currentTransmission.getSender(), currentTransmission.getReceiver());
        this.terminated = false;
    }

    @Override
    public boolean isTerminated () {
        return terminated;
    }

    @Override
    public GraphicSensor getSender () {
        return (GraphicSensor) super.getSender();
    }

    @Override
    public GraphicSensor getReceiver () {
        return (GraphicSensor) super.getReceiver();
    }


}
