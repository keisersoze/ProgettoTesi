package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.model.Sensor;
import app.utils.MyLib;
import app.sim.SimContext;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import org.apache.commons.math3.geometry.spherical.twod.Circle;

import java.util.HashMap;
import java.util.Map;

public class MoveSensors implements Action {

    private final Map<Sensor, Vector3f> sensorsDeployment;
    private Map<Sensor, Vector3f> sensorsDirections;
    private boolean collected;

    public MoveSensors() {
        this.sensorsDeployment = new HashMap<>();
        this.sensorsDirections = new HashMap<>();
        collected = false;
    }

    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();
        if (!collected) {
            context.getSensors().forEach(sensor -> sensorsDeployment.put(sensor, new Vector3f(sensor.getPosition())));
            for (Map.Entry<Sensor, Vector3f> entry : sensorsDeployment.entrySet()) {
                setNewDirection(entry.getKey());
            }
            collected = true;
        }


        for (Sensor sensor : context.getSensors()) {

            double distance = H20Sim.MOVEMENT_SPEED * H20Sim.MOVE_REFRESH;
            double total = sensor.getPosition().distance(sensorsDirections.get(sensor));
            double scalar = distance / total <= 1 ? distance / total : 1;
            sensor.setX(sensor.getX() + (float) scalar * (sensorsDirections.get(sensor).getX() - sensor.getX()));
            sensor.setZ(sensor.getZ() + (float) scalar * (sensorsDirections.get(sensor).getZ() - sensor.getZ()));
            if (scalar == 1)
                setNewDirection(sensor);
        }

        for (Sensor sensor : context.getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, context));
        }
    }

    private void setNewDirection(Sensor s) {
        double angle = MyLib.random(0, (float) (2 * Math.PI));
        Vector3f v1 = sensorsDeployment.get(s);
        float x = (float) (MyLib.map(s.getY(), 0, H20Sim.FIELD_Y, 5, H20Sim.MOVE_RADIUS) * Math.cos(angle)); // angle is in radians
        float z = (float) (MyLib.map(s.getY(), 0, H20Sim.FIELD_Y, 5, H20Sim.MOVE_RADIUS) * Math.sin(angle));
        sensorsDirections.put(s, new Vector3f(x, v1.y, z).addLocal(v1.x, 0, v1.z));
    }

}
