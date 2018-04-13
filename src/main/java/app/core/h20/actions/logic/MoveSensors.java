package app.core.h20.actions.logic;

import app.Canvas;
import app.core.Action;
import app.core.Event;
import app.model.Sensor;
import app.sim.SimContext;

public class MoveSensors implements Action {

    public MoveSensors () {
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        double rotation_time = context.getSimTime() / 10;

        for (Sensor sensor : context.getSensors()) {
            double raggio = Canvas.map(sensor.getY(), 0, 200, 0, 5);
            sensor.setOffsetPosition((float) (Math.cos(rotation_time / raggio)), 0, (float) (Math.sin(rotation_time / raggio)));
        }
    }

}
