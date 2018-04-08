package app.model.h20G;

import app.sim.h20G.Canvas;
import app.model.Sensor;
import com.jme3.scene.Geometry;

import java.util.List;

public interface GraphicSensor extends Sensor {

    void setCanvas(Canvas canvas);

    Geometry getGeometry();

    void updatePosition();

    List<GraphicSensor> getGraphicNeighbors();
}
