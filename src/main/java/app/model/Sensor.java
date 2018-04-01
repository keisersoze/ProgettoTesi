package app.model;

import java.util.List;

public interface Sensor {

    float getX();

    float getY();

    float getZ();

    void setX(float x);

    void setY(float y);

    void setZ(float z);

    void setPosition(float x, float y, float z);

    List<Sensor> getNeighbors();

    double getEuclideanDistance(Sensor s);

    boolean isSink();


}
