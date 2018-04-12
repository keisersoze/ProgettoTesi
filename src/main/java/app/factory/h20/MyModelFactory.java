package app.factory.h20;

import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.h20.BaseFrame;
import app.model.h20.BaseSensor;
import app.model.h20.BaseTransmission;

public class MyModelFactory implements ModelFactory {
    @Override
    public Sensor getSensor(float x, float y, float z) {
        return new BaseSensor(x, y, z);
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