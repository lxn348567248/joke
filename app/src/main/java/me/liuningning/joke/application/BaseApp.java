package me.liuningning.joke.application;

import android.app.Application;

import me.liuningning.core.exception.GlobalExceptionHandler;
import me.liuningning.core.hotfix.HotFixManager;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalExceptionHandler.getInstance().init(this);
        HotFixManager.getInstance().init(this);
        HotFixManager.getInstance().load();

    }
}
