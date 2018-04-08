package app.model;

public interface ModelComponentsFactory {

    Sensor getSensor(float x, float y, float z);

    Transmission getTransmission(Sensor sender,Sensor receiver);

    Frame getFrame(double size, Sensor sender, Sensor currentOwner);
}
