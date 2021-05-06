package me.liuningning.core.ioc;

import android.view.View;

import java.lang.reflect.Method;

import me.liuningning.core.ioc.annoation.OnClick;

class OnclickInject implements Inject {

    @Override
    public void inject(Finder finder, Object target) {

        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();

        Method[] allMethods = targetClass.getDeclaredMethods();
        for (Method method : allMethods) {
            OnClick onClickAnnotation = method.getAnnotation(OnClick.class);
            if (onClickAnnotation == null) {
                continue;
            }
            int[] viewIds = onClickAnnotation.value();
            if (viewIds != null && viewIds.length > 0) {
                for (int viewId : viewIds) {
                    View targetView = finder.findViewById(viewId);
                    if (targetView != null) {
                        targetView.setOnClickListener(new DeclareViewOnclick(target, method));
                    }
                }
            }

        }
    }
}
