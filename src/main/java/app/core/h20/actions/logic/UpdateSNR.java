package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;

import java.util.List;

public class UpdateSNR implements Action {

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        List<Frame> frames = context.getFrames();

        for (Frame frame : frames) {
            for (Transmission t : frame.getTransmissionHistory()) {
                double noise_power = MyLib.calculateNoise(t.getSender(), t.getReceiver(), context, t.getTime());
                double power_received = Math.pow(10, MyLib.powerReceivedFPL(t.getReceiver().getEuclideanDistance(t.getSender())) / 10);
                if (noise_power != 0 && power_received / noise_power < H20Sim.GAMMA) {
                    t.setSuccessfull(false);
                }

            }
        }
    }
}
