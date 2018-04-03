package app.factory.impl;

import app.core.actions.Action;
import app.core.actions.impl.logic.*;
import app.core.actions.impl.stats.UpdateStats;
import app.core.actions.impl.utility.Reschedule;
import app.core.actions.impl.utility.RescheduleExpRandom;
import app.core.events.Event;
import app.factory.CoreComponentsFactory;

public class MyCoreComponentsFactory implements CoreComponentsFactory {
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

        if(type.equalsIgnoreCase(HandleArrival.id)){
            if (handleArrival == null)
                handleArrival= new HandleArrival();
            return handleArrival;

        } else if(type.equalsIgnoreCase(HandleTrasmission.id)){
            if (handleTrasmission == null)
                handleTrasmission= new HandleTrasmission();
            return handleTrasmission;

        } else if(type.equalsIgnoreCase(HandleEndTrasmission.id)){
            if (handleEndTrasmission == null)
                handleEndTrasmission= new HandleEndTrasmission();
            return handleEndTrasmission;

        } else if(type.equalsIgnoreCase(MoveSensors.id)){
            if (moveSensors == null)
                moveSensors= new MoveSensors();
            return moveSensors ;

        } else if(type.equalsIgnoreCase(UpdateSNR.id)){
            if (updateSNR == null)
                updateSNR= new UpdateSNR();
            return updateSNR;

        }else if(type.equalsIgnoreCase(UpdateStats.id)){
            if (updateStats == null)
                updateStats= new UpdateStats();
            return updateStats;

        }else if(type.equalsIgnoreCase(RescheduleExpRandom.id)){
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

        if(type.equalsIgnoreCase(Reschedule.id)) {
            if (reschedule == null)
                reschedule = new Reschedule(value);
            return reschedule;
        }

        return null;
    }


    @Override
    public Event getEvent(String type) {
        return null;
    }
}
