package com.local.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;


public class CommentEditText extends AppCompatEditText {
    public interface PressBackCallBack {
        void callBack();
    }

    private PressBackCallBack callBack;

    public void setCallBack(PressBackCallBack callBack) {
        this.callBack = callBack;
    }

    public CommentEditText(Context context) {
        super(context);
    }

    public CommentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (callBack != null) {
                callBack.callBack();
            }
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }
}
