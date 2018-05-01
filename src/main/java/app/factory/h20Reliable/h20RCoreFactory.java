package app.factory.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.core.h20.events.SensorFrameEvent;
import app.core.h20.events.TransmissionEvent;
import app.core.h20Reliable.*;
import app.core.h20Reliable.events.SensorTransmissionEvent;
import app.factory.ActionTypes;
import app.factory.CoreFactory;
import app.factory.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;


public class h20RCoreFactory extends MyCoreFactory implements CoreFactory {
    HandleEndReception handleEndReception;
    HandleTransmission handleTransmission;
    HandleAckTransmission handleAckTransmission;
    HandleAckReception handleAckReception;
    HandleEndAckReception handleEndAckReception;
    HandleEndAckTransmission handleEndAckTransmission;


    @Override
    public Action getAction (String type) {

        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase(ActionTypes.HandleEndReception)) {
            if (handleEndReception == null) {
                handleEndReception = new HandleEndReception();
            }
            return handleEndReception;
        }else if (type.equalsIgnoreCase(ActionTypes.HandleTransmission)) {
            if (handleTransmission == null) {
                handleTransmission = new HandleTransmission();
            }
            return handleTransmission;
        } else  if (type.equalsIgnoreCase(ActionTypes.HandleAckTransmission)) {
            if (handleAckTransmission == null) {
                handleAckTransmission = new HandleAckTransmission();
            }
            return handleAckTransmission;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleAckReception)) {
            if (handleAckReception == null) {
                handleAckReception = new HandleAckReception();
            }
            return handleAckReception;
        }else  if (type.equalsIgnoreCase(ActionTypes.HandleEndAckReception)) {
            if (handleEndAckReception == null) {
                handleEndAckReception = new HandleEndAckReception();
            }
            return handleEndAckReception;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleEndAckTransmission)) {
            if (handleEndAckTransmission == null) {
                handleEndAckTransmission = new HandleEndAckTransmission();
            }
            return handleEndAckTransmission;
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
        }

        if (e == null) {
            return super.getEvent(type, time, context, frame, sensor, hop);
        } else { return e; }
    }

    @Override
    public Event getEvent(String type, double time, SimContext context, Transmission transmission) {
        if (type == null) {
            return null;
        }

        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndReceptionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.HandleEndReception));
        } else if (type.equalsIgnoreCase(EventTypes.AckTransmissionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.HandleAckTransmission));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        } else if (type.equalsIgnoreCase(EventTypes.AckReceptionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.HandleAckReception));
        }else if (type.equalsIgnoreCase(EventTypes.EndAckTransmissionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.HandleEndAckTransmission));
            e.addAction(getAction(ActionTypes.UpdateSNR));
        }

        if (e == null) {
            return super.getEvent(type, time, context,transmission);
        } else { return e; }
    }

    @Override
    public Event getEvent(String type, double time, SimContext context, Transmission transmission, Sensor sensor) {
        if (type == null) {
            return null;
        }

        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndAckReceptionEvent)){
            e = new SensorTransmissionEvent(time, context, transmission, sensor);
            e.addAction(getAction(ActionTypes.HandleEndAckReception));
        }

        if (e == null) {
            return super.getEvent(type, time, context, transmission,sensor);
        } else { return e; }
    }
}
