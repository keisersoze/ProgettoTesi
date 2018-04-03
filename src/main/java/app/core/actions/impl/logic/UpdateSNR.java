package app.core.actions.impl.logic;

import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;

public class UpdateSNR implements Action {

    public static final String id="UPDATESNR";

    public UpdateSNR() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

    }
}
