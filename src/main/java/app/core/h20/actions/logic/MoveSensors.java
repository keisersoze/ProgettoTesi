package app.core.h20.actions.logic;

import app.core.Action;
import app.core.Event;
import app.model.Sensor;
import app.sim.SimContext;

public class MoveSensors implements Action {

    public MoveSensors() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        for (Sensor sensor : context.getSensors()) {
            sensor.setOffsetPosition(0.2f, 0.2f, 0.5f);
        }
    }

}
