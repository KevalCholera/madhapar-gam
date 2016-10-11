package com.madhapar.View;

import com.madhapar.Model.NewsObject;

/**
 * Created by smartsense on 10/10/16.
 */

public interface NewsDetailViewInt {
    void onSuccessNewsDetail(NewsObject newsObject);

    void onFailRequest();

    void onFailResponse(String message);

}
