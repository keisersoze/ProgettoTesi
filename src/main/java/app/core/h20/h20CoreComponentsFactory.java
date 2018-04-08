package app.core.h20;

import app.core.Action;
import app.core.h20.actions.logic.*;
import app.core.h20.events.*;
import app.core.ActionTypes;
import app.core.h20.actions.stats.UpdateStats;
import app.core.h20.actions.utility.Reschedule;
import app.core.h20.actions.utility.RescheduleExpRandom;
import app.core.Event;
import app.core.CoreComponentsFactory;
import app.core.EventTypes;
import app.model.Frame;
import app.sim.SimContext;

public class h20CoreComponentsFactory implements CoreComponentsFactory {
    HandleEndTrasmission handleEndTrasmission;
    HandleTrasmission handleTrasmission;
    MoveSensors moveSensors;
    HandleArrival handleArrival;
    UpdateSNR updateSNR;
    UpdateStats updateStats;
    Reschedule reschedule;
    RescheduleExpRandom rescheduleExpRandom;


    @Override
    public Action getAction(String type) {

        if(type == null){
            return null;
        }

        if(type.equalsIgnoreCase(ActionTypes.HandleArrival)){
            if (handleArrival == null)
                handleArrival= new HandleArrival();
            return handleArrival;

        } else if(type.equalsIgnoreCase(ActionTypes.HandleTrasmission)){
            if (handleTrasmission == null)
                handleTrasmission= new HandleTrasmission();
            return handleTrasmission;

        } else if(type.equalsIgnoreCase(ActionTypes.HandleEndTrasmission)){
            if (handleEndTrasmission == null)
                handleEndTrasmission= new HandleEndTrasmission();
            return handleEndTrasmission;

        } else if(type.equalsIgnoreCase(ActionTypes.MoveSensors)){
            if (moveSensors == null)
                moveSensors= new MoveSensors();
            return moveSensors ;

        } else if(type.equalsIgnoreCase(ActionTypes.UpdateSNR)){
            if (updateSNR == null)
                updateSNR= new UpdateSNR();
            return updateSNR;

        }else if(type.equalsIgnoreCase(ActionTypes.UpdateStats)){
            if (updateStats == null)
                updateStats= new UpdateStats();
            return updateStats;

        }else if(type.equalsIgnoreCase(ActionTypes.RescheduleExpRandom)){
            if (rescheduleExpRandom == null)
                rescheduleExpRandom= new RescheduleExpRandom();
            return rescheduleExpRandom;
        }

        return null;
    }

    public Action getAction(String type,double value) {

        if(type == null){
            return null;
        }

        if(type.equalsIgnoreCase(ActionTypes.Reschedule)) {
            if (reschedule == null)
                reschedule = new Reschedule(value);
            return reschedule;
        }

        return null;
    }


    @Override
    public Event getEvent(String type,double time, SimContext context) {
        if(type == null){
            return null;
        }
        Event e = null;
        if(type.equalsIgnoreCase(EventTypes.ArrivalEvent)){
            e = new ArrivalEvent(time, context);
            e.addAction(new HandleArrival());
            e.addAction(new RescheduleExpRandom());

        } else if(type.equalsIgnoreCase(EventTypes.BaseEvent)){
            e = new BaseEvent(time, context);

        } else if(type.equalsIgnoreCase(EventTypes.MoveEvent)){
            e = new MoveEvent(time, context);
            e.addAction(new MoveSensors());
            e.addAction(new UpdateSNR());

        } else if(type.equalsIgnoreCase(EventTypes.StatisticEvent)){
            e = new StatisticsEvent(time, context);
            e.addAction(new UpdateStats());
        }

        return e;

    }

    @Override
    public Event getEvent(String type, double time, SimContext context, Frame frame) {
        if(type == null){
            return null;
        }

        Event e = null;
        if(type.equalsIgnoreCase(EventTypes.EndTrasmissionEvent)){
            e = new EndTrasmissionEvent(time, context, frame);
            e.addAction(new HandleEndTrasmission());
            e.addAction(new UpdateSNR());
        } else if(type.equalsIgnoreCase(EventTypes.TrasmissionEvent)){
            e = new TrasmissionEvent(time, context, frame);
            e.addAction(new HandleTrasmission());
            e.addAction(new UpdateSNR());
        }

        return e;

    }
}
