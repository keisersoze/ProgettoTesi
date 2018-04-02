package app.model;

public interface Frame {


    public double getSize();

    public Trasmission getCurrentTransmission();

    public void setCurrentTransmission(Trasmission currentTransmission);

    public Sensor getSender();

    public Sensor getCurrentOwner();

    public void setCurrentOwner(Sensor currentOwner);

    public Trasmission getLastEndedTrasmission();
}
