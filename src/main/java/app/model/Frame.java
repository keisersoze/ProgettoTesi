package app.model;

import java.util.List;

public interface Frame {

    int getSerial ();

    double getSize ();

    Sensor getOwner ();

    List<Transmission> getTransmissions ();

    double getArrivalTime ();
}
