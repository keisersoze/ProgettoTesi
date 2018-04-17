package app.model;

import com.jme3.math.Vector3f;

import java.util.List;

public interface Sensor {

    float getX ();

    void setX (float x);

    float getY ();

    void setY (float y);

    float getZ ();

    void setZ (float z);

    void setPosition (float x, float y, float z);

    Vector3f getPosition ();

    List<Sensor> getNeighbors ();

    void setNeighbors (List<Sensor> list);

    double getEuclideanDistance (Sensor s);

    boolean isSink ();

    void setSink (boolean x);

    void setOffsetPosition (float x, float y, float z);

    boolean isTransmitting();

    void setTransmitting(boolean x);

    boolean isReceiving();

    void setReceiving(boolean x);
}
