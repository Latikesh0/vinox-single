package com.my.mert;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import androidx.appcompat.widget.AppCompatEditText;

public class BackspaceEditText extends AppCompatEditText {

    public interface OnBackspaceListener {
        void onBackspacePressed(BackspaceEditText editText);
    }

    private OnBackspaceListener backspaceListener;
    private long lastBackspaceTime = 0;

    public BackspaceEditText(Context context) {
        super(context);
    }

    public BackspaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackspaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnBackspaceListener(OnBackspaceListener listener) {
        this.backspaceListener = listener;
    }

    private void dispatchBackspace() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackspaceTime < 100) {
            return;
        }
        lastBackspaceTime = currentTime;
        if (backspaceListener != null) {
            backspaceListener.onBackspacePressed(BackspaceEditText.this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            dispatchBackspace();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        if (conn == null) return null;
        return new InputConnectionWrapper(conn, true) {
            @Override
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                if ((beforeLength == 1 && afterLength == 0) || (getText() != null && getText().length() == 0)) {
                    dispatchBackspace();
                    return true;
                }
                return super.deleteSurroundingText(beforeLength, afterLength);
            }

            @Override
            public boolean sendKeyEvent(KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    dispatchBackspace();
                    return true;
                }
                return super.sendKeyEvent(event);
            }
        };
    }
}
