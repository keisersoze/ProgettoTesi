package app.model.h20G.impl;

import app.model.h20G.GModelComponentsFactory;
import app.model.Sensor;
import app.model.h20G.GraphicFrame;
import app.model.h20G.GraphicSensor;
import app.model.h20G.GraphicTransmission;

public class Jme3ModelComponentsFactory implements GModelComponentsFactory {

    @Override
    public GraphicSensor getSensor(float x, float y, float z) {
        return new JmeSensor(x,y,z);
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
