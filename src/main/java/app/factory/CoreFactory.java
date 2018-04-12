package app.factory;

import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.sim.SimContext;

public interface CoreFactory {

    Action getAction(String type);

    Action getAction(String type, double value);

    Event getEvent(String type, double time, SimContext context);

    Event getEvent(String type, double time, SimContext context, Frame frame);


}
