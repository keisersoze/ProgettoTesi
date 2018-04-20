package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.model.Sensor;
import app.sim.MyLib;
import app.sim.SimContext;

public class MoveSensors implements Action {

    public MoveSensors () {
    }

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();

        for (Sensor sensor : context.getSensors()) {
            double raggio = MyLib.map(sensor.getY(), 0, 100, 0, H20Sim.MOVEMENT_SPEED);
            double xOff = MyLib.random(0, (float) Math.PI);
            double yOff = MyLib.random((float) -Math.PI / 2f, (float) Math.PI / 2f);

            sensor.setOffsetPosition((float) (Math.cos(xOff) * raggio), 0, (float) (Math.sin(yOff) * raggio));
        }
        for (Sensor sensor : context.getSensors()) {
            sensor.setNeighbors(MyLib.calculateNeighbors(sensor, context));
        }
    }

}
