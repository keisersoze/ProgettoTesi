package app.core.h20.actions.logic;

import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Transmission;
import app.sim.SimContext;

import java.util.List;

public class UpdateSNR implements Action {

    public UpdateSNR () {
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();

        List<Frame> frames = context.getFrames();


        /*
        for (Frame frame: frames) {
            for (Transmission t: frame.getTransmissionHistory()) {
                double noise_power = (t.getReceiver());
                if
            }
        }
        */

    }

}
