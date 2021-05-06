package me.liuningning.core.ioc;

import android.view.View;

 class ViewFinder implements Finder {
    private View mView;

    public ViewFinder(View view) {
        this.mView = view;
    }

    @Override
    public View findViewById(int viewId) {
        return mView.findViewById(viewId);
    }
}
