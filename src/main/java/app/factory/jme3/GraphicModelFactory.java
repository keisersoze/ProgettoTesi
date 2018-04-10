package app.factory.jme3;

import app.factory.ModelFactory;
import app.model.Sensor;
import app.model.jme3.GraphicFrame;
import app.model.jme3.GraphicSensor;
import app.model.jme3.GraphicTransmission;

public interface GraphicModelFactory extends ModelFactory {
    GraphicSensor getSensor(float x, float y, float z);

    GraphicTransmission getTransmission(Sensor sender, Sensor receiver);

    GraphicFrame getFrame(double size, Sensor sender, Sensor currentOwner);
}
