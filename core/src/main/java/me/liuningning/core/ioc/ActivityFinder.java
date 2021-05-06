package me.liuningning.core.ioc;

import android.app.Activity;
import android.view.View;

class ActivityFinder implements Finder {
    private Activity mActivity;

    ActivityFinder(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public View findViewById(int viewId) {
        return mActivity.findViewById(viewId);
    }
}
