package me.liuningning.core.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import me.liuningning.core.R;

class DialogController {

    private CommonDialog mDialog;

    private ViewHelper mViewHelper;

    DialogController(CommonDialog dialog) {
        this.mDialog = dialog;

    }

    public void setViewHelper(ViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }


    public void setViewOnClick(int viewId, View.OnClickListener listener) {
        mViewHelper.setViewOnClick(viewId, listener);
    }


    public void setText(int viewId, String text) {
        mViewHelper.setText(viewId, text);
    }





    static class Parameter {
        public Context mContext;
        public View mContentView;
        public int mContentViewId = View.NO_ID;
        public SparseArray<String> mTextViews = new SparseArray();
        public SparseArray<View.OnClickListener> mViewClickListeners = new SparseArray<>();
        public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mThemeId = R.style.commonDialog;
        public int mAnimation = 0;

        public void apply(DialogController controller) {
            if (mContentView == null && mContentViewId == View.NO_ID) {
                throw new IllegalArgumentException("must set contView or mContentViewId");
            }

            ViewHelper viewHelper = null;
            if (mContentView != null) {
                viewHelper = new ViewHelper(mContentView);
            }


            if (mContentViewId != View.NO_ID) {
                View contentView = LayoutInflater.from(mContext).inflate(mContentViewId, null);
                viewHelper = new ViewHelper(contentView);
            }

            controller.setViewHelper(viewHelper);


            int size = mTextViews.size();
            for (int i = 0; i < size; i++) {
                controller.setText(mTextViews.keyAt(i), mTextViews.valueAt(i));
            }
            size = mViewClickListeners.size();
            for (int i = 0; i < size; i++) {
                controller.setViewOnClick(mViewClickListeners.keyAt(i), mViewClickListeners.valueAt(i));
            }

            CommonDialog dialog = controller.getDialog();
            dialog.setContentView(viewHelper.getRootView());


            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            lp.height = height;

            window.setAttributes(lp);
            window.setGravity(mGravity);

            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
        }
    }

    private CommonDialog getDialog() {
        return mDialog;
    }


}
