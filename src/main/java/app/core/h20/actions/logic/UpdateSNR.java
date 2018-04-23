package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Transmission;
import app.sim.MyLib;
import app.sim.SimContext;

import java.util.List;

public class UpdateSNR implements Action {

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        List<Frame> frames = context.getFrames();

        for (Frame frame : frames) {
            for (Transmission t : frame.getTransmissionHistory()) {
                double noise_power = MyLib.calculateNoise(t.getReceiver(), context);
                if (MyLib.powerReceived(t.getReceiver().getEuclideanDistance(t.getSender())) / noise_power > H20Sim.GAMMA) {
                    t.setSuccessfull(false);
                }

            }
        }
    }
}
