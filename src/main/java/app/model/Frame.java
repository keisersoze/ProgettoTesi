package app.model;

import java.util.List;

public interface Frame <S extends Sensor,T extends Transmission>{

    public double getSize();

    public T getCurrentTransmission();

    public void setCurrentTransmission(T currentTransmission);

    public Sensor getSender();

    public Sensor getCurrentOwner();

    public void setCurrentOwner(S currentOwner);

    public List<T> getTransmissionHistory();

    public boolean isArrived();

    public void setArrived(boolean arrived);
}
