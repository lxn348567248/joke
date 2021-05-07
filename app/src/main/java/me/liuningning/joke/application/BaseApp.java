package me.liuningning.joke.application;

import android.app.Application;

import me.liuningning.core.exception.GlobalExceptionHandler;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalExceptionHandler.getInstance().init(this);
    }
}
