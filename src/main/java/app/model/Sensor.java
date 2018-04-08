package app.model;

import java.util.List;

public interface Sensor <S extends Sensor> {

    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    float getZ();

    void setZ(float z);

    void setPosition(float x, float y, float z);

    boolean isSink();

    void setSink(boolean x);

    void setOffsetPosition(float x, float y, float z);

    List<S> getNeighbors();

    double getEuclideanDistance(S s);

}
