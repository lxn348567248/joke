package me.liuningning.core.ioc;

import android.view.View;

import java.lang.reflect.Method;

class DeclareViewOnclick implements View.OnClickListener {
    private Method mInvokeMethod;
    private Object mTarget;

    DeclareViewOnclick(Object target, Method method) {
        this.mInvokeMethod = method;
        this.mTarget = target;
    }

    @Override
    public void onClick(View v) {
        try {
            mInvokeMethod.setAccessible(true);
            mInvokeMethod.invoke(mTarget, v);
        } catch (Exception e) {

        }

    }

}
