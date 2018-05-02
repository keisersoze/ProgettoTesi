package app.factory;

import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public interface CoreFactory {

    Action getAction (String type);

    Action getAction (String type, double value);

    Event getEvent (String type, double time, SimContext context);

    Event getEvent (String type, double time, SimContext context, Transmission transmission);

    Event getEvent (String type, double time, SimContext context, Frame frame, Sensor sensor, int hop);

    Event getEvent (String type, double time, SimContext context, Sensor sensor);

    Event getEvent (String type, double time, SimContext context, Transmission transmission, Sensor sensor);

}
