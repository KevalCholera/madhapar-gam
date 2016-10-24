package com.madhapar.Model;

import android.text.TextUtils;

/**
 * Created by Ronak on 10/24/2016.
 */
public class CommentModel implements CommentModelInt {
    @Override
    public void comment(String comment, onCommentRequestFinishListener listener) {
        if (TextUtils.isEmpty(comment)) {
            listener.onCommentBlankError();
        } else if (TextUtils.isEmpty(comment)) {
            listener.onCommentLengthError();
        }
    }
}
