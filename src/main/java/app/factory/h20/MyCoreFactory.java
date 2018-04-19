package app.factory.h20;

import app.core.Action;
import app.core.Event;
import app.core.h20.actions.logic.*;
import app.core.h20.actions.stats.UpdateStats;
import app.core.h20.actions.utility.Reschedule;
import app.core.h20.actions.utility.RescheduleExpRandom;
import app.core.h20.events.BaseEvent;
import app.core.h20.events.SensorEvent;
import app.core.h20.events.SensorFrameEvent;
import app.core.h20.events.TransmissionEvent;
import app.factory.CoreFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class MyCoreFactory implements CoreFactory {
    private HandleEndReception handleEndReception;
    private HandleTransmission handleTransmission;
    private MoveSensors moveSensors;
    private HandleArrival handleArrival;
    private UpdateSNR updateSNR;
    private UpdateStats updateStats;
    private Reschedule reschedule;
    private RescheduleExpRandom rescheduleExpRandom;
    private HandleReception handleReception;
    private HandleEndTransmission handleEndTransmission;


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

        } else if (type.equalsIgnoreCase(ActionTypes.HandleTransmission)) {
            if (handleTransmission == null) {
                handleTransmission = new HandleTransmission();
            }
            return handleTransmission;

        } else if (type.equalsIgnoreCase(ActionTypes.HandleArrival)) {
            if (handleArrival == null) {
                handleArrival = new HandleArrival();
            }
            return handleArrival;

        } else if (type.equalsIgnoreCase(ActionTypes.MoveSensors)) {
            if (moveSensors == null) {
                moveSensors = new MoveSensors();
            }
            return moveSensors;

        } else if (type.equalsIgnoreCase(ActionTypes.UpdateSNR)) {
            if (updateSNR == null) {
                updateSNR = new UpdateSNR();
            }
            return updateSNR;

        } else if (type.equalsIgnoreCase(ActionTypes.UpdateStats)) {
            if (updateStats == null) {
                updateStats = new UpdateStats();
            }
            return updateStats;

        } else if (type.equalsIgnoreCase(ActionTypes.RescheduleExpRandom)) {
            if (rescheduleExpRandom == null) {
                rescheduleExpRandom = new RescheduleExpRandom();
            }
            return rescheduleExpRandom;
        } else if (type.equalsIgnoreCase(ActionTypes.HandleReception)) {
            if (handleReception == null) {
                handleReception = new HandleReception();
            }
            return handleReception;
        }else if (type.equalsIgnoreCase(ActionTypes.HandleEndTransmission)) {
            if (handleEndTransmission == null) {
                handleEndTransmission = new HandleEndTransmission();
            }
            return handleEndTransmission;
        }

        return null;
    }

    public Action getAction (String type, double value) {

        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase(ActionTypes.Reschedule)) {
            if (reschedule == null) {
                reschedule = new Reschedule(value);
            }
            return reschedule;
        }

        return null;
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
            e.addAction(getAction(ActionTypes.RescheduleExpRandom));           //TODO: da decommentare finito il testing

        } else if (type.equalsIgnoreCase(EventTypes.BaseEvent)) {
            e = new BaseEvent(time, context);

        } else if (type.equalsIgnoreCase(EventTypes.MoveEvent)) {
            e = new BaseEvent(time, context);
            e.addAction(getAction(ActionTypes.MoveSensors));
            e.addAction(getAction(ActionTypes.UpdateSNR));

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
            e.addAction(getAction(ActionTypes.HandleEndReception));
            e.addAction(getAction(ActionTypes.UpdateSNR));

        } else if (type.equalsIgnoreCase((EventTypes.ReceivingTransmissionEvent))) {
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
            e.addAction(getAction(ActionTypes.UpdateSNR));
        }
        return e;
    }

    @Override
    public Event getEvent(String type, double time, SimContext context, Sensor sensor) {

        if (type == null) {
            return null;
        }
        Event e = null;
        if (type.equalsIgnoreCase(EventTypes.EndTransmissionEvent)) {
            e = new SensorEvent(time, context,sensor);
            e.addAction(getAction(ActionTypes.HandleEndTransmission));
        }

        return e;
    }
}
