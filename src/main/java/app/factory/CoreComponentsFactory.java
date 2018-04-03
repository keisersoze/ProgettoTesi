package app.factory;

import app.core.actions.Action;
import app.core.events.Event;

public interface CoreComponentsFactory {

    Action getAction(String type);

    Action getAction(String type,double value);

    Event getEvent(String type);



}
