package app.factory.H20Probabilistic;

import app.core.Action;
import app.core.Event;
import app.core.h20.events.BaseEvent;
import app.core.h20.events.SensorEvent;
import app.core.h20.events.SensorFrameEvent;
import app.core.h20.events.TransmissionEvent;
import app.core.h20Probabilistic.HandleEndReception;
import app.core.h20Probabilistic.HandleTransmission;
import app.core.h20Probabilistic.ProbabilisticReception;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.factory.h20.MyCoreFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class ProbabilisticCoreFactory extends MyCoreFactory {
    private ProbabilisticReception probabilisticReception;
    private HandleEndReception handleEndReception;
    private HandleTransmission handleTransmission;

    @Override
    public Action getAction (String type) {
        if (type.equalsIgnoreCase(ActionTypes.ProbabilisticReception)) {
            if (probabilisticReception == null) {
                probabilisticReception = new ProbabilisticReception();
            }
            return probabilisticReception;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleEndReception)) {
            if (handleEndReception == null) {
                handleEndReception = new HandleEndReception();
            }
            return handleEndReception;

        } else if (type.equalsIgnoreCase(ActionTypes.HandleTransmission)) {
            if (handleTransmission == null) {
                handleTransmission = new HandleTransmission();
            }
            return handleTransmission;

        }
        return super.getAction(type);
    }

    @Override
    public Event getEvent (String type, double time, SimContext context) {
        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.ArrivalEvent)) {
            e = new BaseEvent(time, context);
            e.addAction(getAction(ActionTypes.HandleArrival));
            e.addAction(getAction(ActionTypes.RescheduleExpRandom));

        } else if (type.equalsIgnoreCase(EventTypes.BaseEvent)) {
            e = new BaseEvent(time, context);

        } else if (type.equalsIgnoreCase(EventTypes.MoveEvent)) {
            e = new BaseEvent(time, context);
            e.addAction(getAction(ActionTypes.MoveSensors));

        } else if (type.equalsIgnoreCase(EventTypes.StatisticEvent)) {
            e = new BaseEvent(time, context);
            e.addAction(getAction(ActionTypes.UpdateStats));

        }

        return e;

    }

    @Override
    public Event getEvent (String type, double time, SimContext context, Transmission transmission) {
        if (type == null) {
            return null;
        }

        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndReceptionEvent)) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.ProbabilisticReception));
            e.addAction(getAction(ActionTypes.HandleEndReception));

        } else if (type.equalsIgnoreCase((EventTypes.ReceptionEvent))) {
            e = new TransmissionEvent(time, context, transmission);
            e.addAction(getAction(ActionTypes.HandleReception));
        }

        return e;
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
        }
        return e;
    }

    @Override
    public Event getEvent (String type, double time, SimContext context, Sensor sensor) {

        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndTransmissionEvent)) {
            e = new SensorEvent(time, context, sensor);
            e.addAction(getAction(ActionTypes.HandleEndTransmission));
        }

        return e;
    }
}
