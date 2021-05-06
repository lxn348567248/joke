package me.liuningning.core.ioc;

import android.view.View;

import java.lang.reflect.Field;

import me.liuningning.core.ioc.annoation.ViewInject;

class ViewInjects implements Inject {


    public void inject(Finder finder, Object target) {

        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();
        Field[] allFields = targetClass.getDeclaredFields();

        for (Field field : allFields) {
            ViewInject viewInjectAnnotation = field.getAnnotation(ViewInject.class);
            if (viewInjectAnnotation == null) {
                continue;
            }
            int viewId = viewInjectAnnotation.value();
            if (viewId != View.NO_ID) {
                View targetView = finder.findViewById(viewId);
                if (targetView != null) {
                    setFieldValue(target, field, targetView);
                }
            }


        }


    }


    private static void setFieldValue(Object target, Field field, View targetView) {
        try {
            field.setAccessible(true);
            field.set(target, targetView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
