package com.madhapar.View;

/**
 * Created by Ronak on 9/29/2016.
 */
public class EventCalender {
    private String eventName,dateEvent,address,going,interest,cantgo;

    public EventCalender(String eventName,String dateEvent,String address,String going,String interest,String cantgo){
        this.address = address;
        this.cantgo = cantgo;
        this.dateEvent = dateEvent;
        this.eventName = eventName;
        this.going = going;
        this.interest = interest;
    }
    public String getEventName(){
        return eventName;
    }
    public void setEventName(String eventName){this.eventName = eventName;}
    public String getDateEvent(){
        return dateEvent;
    }
    public void setDateEvent(String dateEvent){this.dateEvent = dateEvent;}
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){this.address = address;}
    public String getGoing(){
        return going;
    }
    public void setGoing(String going){this.going = going;}
    public String getInterest(){
        return interest;
    }
    public void setInterest(String interest){this.interest = interest;}
    public String getCantgo(){
        return cantgo;
    }
    public void setCantgo(String cantgo){this.cantgo = cantgo;}
}
