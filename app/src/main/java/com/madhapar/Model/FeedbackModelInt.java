package com.madhapar.Model;

/**
 * Created by Ronak on 9/29/2016.
 */
public interface FeedbackModelInt {
    interface OnLoginFinishedListener {
        
        void onFeddbackSubjectError();

        void onFeddbackDescriptionError();

        void onFeddbackRequiredFieldError();

        void onFeedbackSuccess();
    }
     void feedback(String name,String mobile,String subject,String feedback, OnLoginFinishedListener listener);
}
