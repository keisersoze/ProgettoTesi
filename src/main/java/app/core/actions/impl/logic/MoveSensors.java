package app.core.actions.impl.logic;

import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;
import app.model.Sensor;

public class MoveSensors implements Action {

    public static final String id="MOVESENSORS";

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
