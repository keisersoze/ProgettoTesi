package app.model;

public interface ModelComponentsFactory <S extends Sensor,T extends Transmission, F extends Frame> {

    S getSensor(float x, float y, float z);

    T getTransmission(S sender,S receiver);

    F getFrame(double size, S sender, S currentOwner);
}
