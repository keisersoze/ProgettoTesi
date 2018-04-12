package app.model.jme3;

import app.Canvas;
import app.model.Sensor;
import com.jme3.scene.Geometry;

public interface GraphicSensor extends Sensor {

    void draw (Canvas canvas);

    Geometry getGeometry ();

    void updatePosition ();

}
