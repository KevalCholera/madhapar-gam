package com.madhapar.Presenter;

import com.madhapar.Model.EventCalenderModelInt;
import com.madhapar.View.EventListInt;

/**
 * Created by smartsense on 06/10/16.
 */

public interface RequestPresenterInt {
    void getEventList(EventListInt callback);
}
