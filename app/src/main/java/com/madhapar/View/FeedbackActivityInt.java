package com.madhapar.View;

/**
 * Created by Ronak on 9/29/2016.
 */
public interface FeedbackActivityInt {
    void feedbackValidateResult(int check);


    void onFeedbackResponseError(String meddaasge);

    void onFeedbackSuccess(String message);

    void onFeedbackRequestError();
}
