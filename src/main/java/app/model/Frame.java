package app.model;

public interface Frame {


    public double getSize();

    public Trasmission getCurrentTransmission();

    public Sensor getSender();

    public Sensor getCurrentOwner();

    public void setCurrentTransmission(Trasmission currentTransmission);

    public void setCurrentOwner(Sensor currentOwner);

}
