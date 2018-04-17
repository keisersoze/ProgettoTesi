package app.core.h20.actions.logic;

import app.core.Action;
import app.core.Event;

public class HandleEndTransmission implements Action {
    @Override
    public void execute (Event e) {
        e.getSensor().setTransmitting(false);
    }
}
