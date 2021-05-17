package me.liuningning.joke.application;

import android.app.Application;

import me.liuningning.core.exception.GlobalExceptionHandler;
import me.liuningning.core.hotfix.HotFixManager;
import me.liuningning.framework.skin.SkinManager;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
//        GlobalExceptionHandler.getInstance().init(this);
//        HotFixManager.getInstance().init(this);
//        HotFixManager.getInstance().load();

    }
}
