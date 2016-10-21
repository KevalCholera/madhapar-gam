package com.madhapar.Model;

/**
 * Created by Ronak on 9/29/2016.
 */
public interface FeedbackModelInt {
    interface OnFeedbackPostListener {

        void onFeedbackFieldRequiredError();

        void onFeedbackSubjectRequiredError();

        void onFeedbackSubjectValidError();

        void onFeedbackRequiredError();

        void onFeedbackValidError();

        void onSuccessPostFeedback(String messsage);
        void onFailFeedbackResponse(String message);
        void onFailFeedbackRequest();
    }

    void feedback(String subject, String feedback, OnFeedbackPostListener listener);
}
