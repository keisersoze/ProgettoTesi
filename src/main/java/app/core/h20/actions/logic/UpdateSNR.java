package app.core.h20.actions.logic;

import app.H2OSim;
import app.MyLib;
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



        for (Frame frame: frames) {
            for (Transmission t: frame.getTransmissionHistory()) {
                //TODO t deve essere in uno stato particolare
                double noise_power = MyLib.calculateNoise(t.getReceiver(),context);
                if (MyLib.powerReceived(t.getReceiver().getEuclideanDistance(t.getSender()))/noise_power> H2OSim.T){
                    t.setSuccessfull(false);
                }
            }
        }


    }

}
