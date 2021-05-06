package me.liuningning.core.ioc;

import android.app.Activity;
import android.view.View;


public class Injects {
    private Injects() {
        throw new UnsupportedOperationException("not support instance");
    }

    public static void inject(Activity activity) {
        inject(new ActivityFinder(activity), activity);

    }

    public static void inject(View view, Object target) {
        inject(new ViewFinder(view), target);
    }

    private static void inject(Finder finder, Object target) {

        ViewInjects viewInject = new ViewInjects();
        viewInject.inject(finder, target);

        OnclickInject onclickInject = new OnclickInject();
        onclickInject.inject(finder, target);


    }


}
