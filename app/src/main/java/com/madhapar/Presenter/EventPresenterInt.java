package com.madhapar.Presenter;

import com.madhapar.View.EventDetailCallback;

/**
 * Created by smartsense on 12/10/16.
 */

public interface EventPresenterInt {
    void getEventDetail(String eventId, EventDetailCallback mEventDetailCallback);

    void getEventList(EventDetailCallback.EventListCallback callback);

    void getEventStatusList(String eventId, String eventStatus, EventDetailCallback.EventStatusListCallback mEventStatusListCallback);

    void createEventStatus(String eventId, String eventStatus, String count, EventDetailCallback.EventStatusCreateCallback mEventStatusCreateCallback);
    void updateEventStatus(String eventId,String eventStatus,String count,EventDetailCallback.EventStatusUpdateCallback mEventStatusUpdateCallback);
}
