package app.factory.jme3.impl;

import app.factory.jme3.GraphicModelFactory;
import app.model.Sensor;
import app.model.jme3.GraphicFrame;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;
import app.model.jme3.impl.JmeFrame;
import app.model.jme3.impl.JmeSensor;
import app.model.jme3.impl.JmeTransmission;

public class JmeModelFactory implements GraphicModelFactory {

    @Override
    public GraphicSensor getSensor(float x, float y, float z) {
        return new JmeSensor(x, y, z);
    }

    @Override
    public GraphicTransmission getTransmission(Sensor sender, Sensor receiver) {
        return new JmeTransmission(sender, receiver);
    }

    @Override
    public GraphicFrame getFrame(double size, Sensor sender, Sensor currentOwner) {
        return new JmeFrame(size, sender, currentOwner);
    }
}
