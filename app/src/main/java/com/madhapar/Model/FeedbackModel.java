package com.madhapar.Model;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Ronak on 9/29/2016.
 */
public class FeedbackModel implements FeedbackModelInt{
    @Override
    public void feedback(final String name, final String mobile, final String subject, final String feedback, final OnLoginFinishedListener listener) {
                boolean error = false;
                if (TextUtils.isEmpty(subject) && TextUtils.isEmpty(name) && TextUtils.isEmpty(mobile) && TextUtils.isEmpty(feedback)) {
                    listener.onFeddbackRequiredFieldError();
                    error = true;
                }
                else if (TextUtils.isEmpty(subject)) {
                    listener.onFeddbackSubjectError();
                    error = true;
                }
                else if (TextUtils.isEmpty(feedback)) {
                    listener.onFeddbackDescriptionError();
                    error = true;
                }
                if (!error) {
                    listener.onFeedbackSuccess();
                }
            }
}
