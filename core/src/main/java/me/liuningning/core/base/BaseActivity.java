package me.liuningning.core.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.liuningning.core.ioc.Injects;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        Injects.inject(this);
        initTitleView();
        initView();
        initData();
    }

    protected abstract int layoutId();

    protected abstract void initTitleView();

    protected abstract void initView();

    protected abstract void initData();


}
