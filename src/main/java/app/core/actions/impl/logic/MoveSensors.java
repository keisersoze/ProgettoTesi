package app.core.actions.impl.logic;

import app.sim.SimContext;
import app.core.actions.Action;
import app.model.Sensor;

public class MoveSensors implements Action {

    @Override
    public void execute(SimContext context) {

        for (Sensor sensor : context.getSensors()) {
            sensor.setOffsetPosition(0.2f, 0.2f, 0.5f);
        }
    }
}
