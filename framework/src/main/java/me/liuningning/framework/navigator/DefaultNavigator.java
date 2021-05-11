package me.liuningning.framework.navigator;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import me.liuningning.core.navigator.BaseNavigator;
import me.liuningning.framework.R;

public class DefaultNavigator extends BaseNavigator<DefaultNavigator.Parameter> {

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mParameter.mContext instanceof Activity) {
                Activity activity = (Activity) mParameter.mContext;
                activity.finish();
            }
        }
    };

    protected DefaultNavigator(Parameter parameter) {
        super(parameter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void bindView() {

        setText(R.id.id_default_navigator_title, mParameter.mTitle);
        setText(R.id.id_default_navigator_right, mParameter.mRight);
        setVisibility(R.id.id_default_navigator_back, View.VISIBLE);

        if (mParameter.mLeftListener == null) {
            mParameter.mLeftListener = mBackListener;
        }
        setViewClickListener(R.id.id_default_navigator_back, mParameter.mLeftListener);
    }


    public static class Builder extends BaseNavigator.Builder<Parameter, DefaultNavigator> {

        public Builder(Context context, ViewGroup parentView) {
            super(context, parentView);
        }

        @Override
        protected Parameter createParameter(Context context, ViewGroup parentView) {
            return new Parameter(context, parentView);
        }

        @Override
        protected DefaultNavigator create() {
            return new DefaultNavigator(this.mParameter);
        }

        public Builder setTitle(String title) {
            mParameter.mTitle = title;
            return this;
        }

        public Builder setRight(String right) {
            mParameter.mRight = right;
            return this;
        }

        public void setLeftVisibility(int visibility) {
            mParameter.mLeftVisibility = visibility;
        }


    }

    static class Parameter extends BaseNavigator.Parameter {
        View.OnClickListener mLeftListener;
        int mLeftVisibility = View.VISIBLE;
        String mTitle;
        String mRight;

        public Parameter(Context context, ViewGroup parentView) {
            super(context, parentView);
        }
    }
}
