package app.model;

import com.jme3.scene.Geometry;

public interface GraphicSensor extends Sensor{
    
    Geometry getGeometry();

    void updatePosition();
}
