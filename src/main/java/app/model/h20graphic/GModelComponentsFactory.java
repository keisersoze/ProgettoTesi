package app.model.h20graphic;

import app.model.ModelComponentsFactory;
import app.model.Sensor;
import app.model.h20graphic.GraphicFrame;
import app.model.h20graphic.GraphicSensor;
import app.model.h20graphic.GraphicTransmission;

public interface GModelComponentsFactory extends ModelComponentsFactory{
    GraphicSensor getSensor(float x, float y, float z);
    GraphicTransmission getTransmission(Sensor sender, Sensor receiver);
    GraphicFrame getFrame(double size, Sensor sender, Sensor currentOwner);
}
