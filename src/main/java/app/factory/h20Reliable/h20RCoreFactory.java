package app.factory.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.core.h20.events.SensorFrameEvent;
import app.core.h20Reliable.*;
import app.factory.ActionTypes;
import app.factory.CoreFactory;
import app.factory.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;


public class h20RCoreFactory extends MyCoreFactory implements CoreFactory {
    HandleTransmission handleTransmission;
    HandleAckTransmission handleAckTransmission;
    HandleAckReception handleAckReception;
    HandleEndReception handleEndReception;


    @Override
    public Action getAction (String type) {

        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase(ActionTypes.HandleAckTransmission)) {
            if (handleAckTransmission == null) {
                handleAckTransmission = new HandleAckTransmission();
            }
            return handleAckTransmission;

        } else if (type.equalsIgnoreCase(ActionTypes.HandleTransmission)) {
            if (handleTransmission == null) {
                handleTransmission = new HandleTransmission();
            }
            return handleTransmission;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleAckReception)) {
            if (handleAckReception == null) {
                handleAckReception = new HandleAckReception();
            }
            return handleAckReception;
        }else if (type.equalsIgnoreCase(ActionTypes.HandleEndReception)) {
            if (handleEndReception == null) {
                handleEndReception = new HandleEndReception();
            }
            return handleEndReception;
        }

        return super.getAction(type);
    }

    @Override
    public Event getEvent (String type, double time, SimContext context, Frame frame, Sensor sensor, int hop) {
        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.TransmissionEvent)) {
            e = new SensorFrameEvent(time, context, frame, sensor, hop);
            e.addAction(getAction(ActionTypes.HandleTransmission));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        } else if (type.equalsIgnoreCase(EventTypes.AckTransmissionEvent)) {
            e = new SensorFrameEvent(time, context, frame, sensor, hop);
            e.addAction(getAction(ActionTypes.HandleAckTransmission));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        } else if (type.equalsIgnoreCase(EventTypes.EndReceptionEvent)) {
            e = new SensorFrameEvent(time, context, frame, sensor, hop);
            e.addAction(getAction(ActionTypes.HandleEndReception));
        }

        if (e == null) {
            return super.getEvent(type, time, context, frame, sensor, hop);
        } else { return e; }
    }

}
