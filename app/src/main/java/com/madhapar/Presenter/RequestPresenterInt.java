package com.madhapar.Presenter;

import com.madhapar.View.EventListInt;
import com.madhapar.View.NetworkViewInt;

/**
 * Created by smartsense on 06/10/16.
 */

public interface RequestPresenterInt {
    void getEventList(EventListInt callback);

    void getNetworkList(NetworkViewInt networkViewInt);
}
