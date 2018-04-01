package app.model;

import java.util.List;

public interface Sensor {

    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    float getZ();

    void setZ(float z);

    void setPosition(float x, float y, float z);

    List<Sensor> getNeighbors();

    double getEuclideanDistance(Sensor s);

    boolean isSink();


}
