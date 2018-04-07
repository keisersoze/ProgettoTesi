package app.factory.jme3;

import app.factory.ModelComponentsFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.jme3.GraphicFrame;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;
import app.model.jme3.impl.JmeFrame;
import app.model.jme3.impl.JmeSensor;
import app.model.jme3.impl.JmeTransmission;

public interface GraphicModelCompFactory extends ModelComponentsFactory{
    GraphicSensor getSensor(float x, float y, float z);
    GraphicTransmission getTransmission(Sensor sender, Sensor receiver);
    GraphicFrame getFrame(double size, Sensor sender, Sensor currentOwner);
}
