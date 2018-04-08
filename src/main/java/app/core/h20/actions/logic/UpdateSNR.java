package app.core.h20.actions.logic;

import app.core.Event;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;
import app.core.Action;

public class UpdateSNR implements Action {

    public UpdateSNR() {
    }

    @Override
    public void execute(Event event) {

        SimContext <Sensor,Frame> context = event.getContext();

    }

}
