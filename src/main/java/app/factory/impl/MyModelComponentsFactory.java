package app.factory.impl;

import app.factory.ModelComponentsFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.impl.BaseFrame;
import app.model.impl.BaseSensor;
import app.model.impl.BaseTransmission;

public class MyModelComponentsFactory implements ModelComponentsFactory {
    @Override
    public Sensor getSensor(float x, float y, float z) {
        return new BaseSensor(x,y,z);
    }

    @Override
    public Transmission getTransmission(Sensor sender, Sensor receiver) {
        return new BaseTransmission(sender, receiver);
    }

    @Override
    public Frame getFrame(double size, Sensor sender, Sensor currentOwner) {
        return new BaseFrame(size, sender, currentOwner);
    }
}
