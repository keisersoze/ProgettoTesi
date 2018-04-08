package app.model.h20;

import app.model.ModelComponentsFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

public class h20ModelComponentsFactory implements ModelComponentsFactory {
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
