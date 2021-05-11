package me.liuningning.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import me.liuningning.core.R;


public class CommonDialog extends Dialog {


    private DialogController mDialogController;

    protected CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mDialogController = new DialogController(this);
    }


    public static class Build {

        private DialogController.Parameter mParameter;


        public Build(Context context) {
            mParameter = new DialogController.Parameter();
            mParameter.mContext = context;
        }

        public Build setThemeId(int themeId) {
            mParameter.mThemeId = themeId;
            return this;
        }

        public Build setContentView(View view) {
            mParameter.mContentView = view;
            return this;
        }

        public Build setContentView(int viewId) {
            mParameter.mContentViewId = viewId;
            return this;
        }

        public Build setText(int resId, String text) {
            mParameter.mTextViews.put(resId, text);
            return this;
        }

        public Build setOnClickListern(int resId, View.OnClickListener clickListener) {
            mParameter.mViewClickListeners.put(resId, clickListener);
            return this;
        }

        public Build fullWith() {
            mParameter.width = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Build animation(int animationId) {
            mParameter.mAnimation = animationId;
            return this;
        }

        public Build addDefaultAnimation() {
            mParameter.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        public Build fromBottom(boolean enable) {
            if (enable) {
                mParameter.mAnimation = R.style.dialog_from_bottom_anim;
            }
            mParameter.mGravity = Gravity.BOTTOM;
            return this;
        }


        public CommonDialog create() {
            CommonDialog dialog = new CommonDialog(mParameter.mContext, mParameter.mThemeId);
            mParameter.apply(dialog.mDialogController);
            return dialog;
        }

    }

}
