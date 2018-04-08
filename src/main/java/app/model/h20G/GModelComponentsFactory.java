package app.model.h20G;

import app.model.ModelComponentsFactory;
import app.model.Sensor;

public interface GModelComponentsFactory extends ModelComponentsFactory{
    GraphicSensor getSensor(float x, float y, float z);
    GraphicTransmission getTransmission(Sensor sender, Sensor receiver);
    GraphicFrame getFrame(double size, Sensor sender, Sensor currentOwner);
}
