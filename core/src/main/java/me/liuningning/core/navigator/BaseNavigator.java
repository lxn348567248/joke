package me.liuningning.core.navigator;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public abstract class BaseNavigator<P extends BaseNavigator.Parameter> implements INavigator {


    protected P mParameter;
    protected View mRootView;

    protected BaseNavigator(P parameter) {
        this.mParameter = parameter;

        ViewGroup parentView = parameter.mParentView;
        if (parameter.mParentView == null && mParameter.mContext instanceof Activity) {
            Activity activity = (Activity) mParameter.mContext;
            parentView = (ViewGroup) activity.getWindow().getDecorView();
            parentView = (ViewGroup) parentView.getChildAt(0);
        }

        mRootView = LayoutInflater.from(parameter.mContext).inflate(getLayoutId(), parentView, false);
        parentView.addView(mRootView, 0);
        bindView();
    }

    protected void setText(int viewId, String text) {
        View view = mRootView.findViewById(viewId);
        if (view instanceof TextView) {
            if (TextUtils.isEmpty(text)) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
                ((TextView) view).setText(text);
            }
        }
    }

    protected void setVisibility(int viewId, int visibility) {
        View view = mRootView.findViewById(viewId);
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(visibility);
        }
    }

    protected void setViewClickListener(int viewId, View.OnClickListener listener) {
        View view = mRootView.findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public abstract static class Builder<P extends Parameter, N extends BaseNavigator<P>> {

        protected P mParameter;

        protected Builder(Context context, ViewGroup parentView) {
            mParameter = createParameter(context, parentView);
        }

        protected abstract P createParameter(Context context, ViewGroup parentView);

        protected abstract N create();

        public void build() {
            N navigator = create();
            navigator.bindView();
        }

    }


    public abstract static class Parameter {

        public Context mContext;
        public ViewGroup mParentView;

        protected Parameter(Context context, ViewGroup parentView) {
            this.mContext = context;
            this.mParentView = parentView;
        }

    }
}
