package com.madhapar.View;

import com.madhapar.Model.NewsObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by smartsense on 08/10/16.
 */

public interface HomeViewInt {
    void onSuccessNewsList(List<NewsObject> newsList);

    void onFailRequest();

    void onFailResponse(String message);

}
