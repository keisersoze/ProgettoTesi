package app.model;

import java.util.LinkedList;

public interface Frame {


    public double getSize();

    public Trasmission getCurrentTransmission();

    public void setCurrentTransmission(Trasmission currentTransmission);

    public Sensor getSender();

    public Sensor getCurrentOwner();

    public void setCurrentOwner(Sensor currentOwner);

    public LinkedList<Trasmission> getTransmissionHistory();

    public boolean isArrived();

    public void setArrived(boolean arrived);
}
