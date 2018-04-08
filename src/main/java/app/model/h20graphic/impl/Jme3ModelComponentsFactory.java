package app.model.h20graphic.impl;

import app.model.h20graphic.GModelComponentsFactory;
import app.model.Sensor;
import app.model.h20graphic.GraphicFrame;
import app.model.h20graphic.GraphicSensor;
import app.model.h20graphic.GraphicTransmission;
import app.model.h20graphic.impl.JmeFrame;
import app.model.h20graphic.impl.JmeSensor;
import app.model.h20graphic.impl.JmeTransmission;

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
