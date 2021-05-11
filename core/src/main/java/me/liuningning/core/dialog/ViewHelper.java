package me.liuningning.core.dialog;

import android.view.View;
import android.widget.TextView;


class ViewHelper {

    private View mRootView;

    ViewHelper(View view) {
        this.mRootView = view;

    }

    public void setViewOnClick(int viewId, View.OnClickListener clickListener) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    public View getRootView() {
        return mRootView;
    }

    public void setText(int viewId, String value) {
        TextView textView = findViewById(viewId);
        if (textView != null) {
            textView.setText(value);
        }
    }

    public final <T extends View> T findViewById(int viewId) {
        return mRootView.findViewById(viewId);
    }
}
