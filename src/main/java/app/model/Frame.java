package app.model;

import java.util.List;

public interface Frame {

    public double getSize();

    public Transmission getCurrentTransmission();

    public void setCurrentTransmission(Transmission currentTransmission);

    public Sensor getSender();

    public Sensor getCurrentOwner();

    public void setCurrentOwner(Sensor currentOwner);

    public List<Transmission> getTransmissionHistory();

    public boolean isArrived();

    public void setArrived(boolean arrived);
}
