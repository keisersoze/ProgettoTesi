package app.factory;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.jme3.GraphicSensor;
import app.model.jme3.impl.JmeFrame;
import app.model.jme3.impl.JmeSensor;
import app.model.jme3.impl.JmeTransmission;

public interface ModelComponentsFactory {

    Sensor getSensor(float x, float y, float z);

    Transmission getTransmission(Sensor sender,Sensor receiver);

    Frame getFrame(double size, Sensor sender, Sensor currentOwner);
}
