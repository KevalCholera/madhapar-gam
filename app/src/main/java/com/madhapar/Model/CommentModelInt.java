package com.madhapar.Model;

/**
 * Created by Ronak on 10/24/2016.
 */
public interface CommentModelInt {
    interface onCommentRequestFinishListener {
        void onCommentBlankError();

        void onCommentLengthError();
    }

    void comment(String comment, onCommentRequestFinishListener listener);

}
