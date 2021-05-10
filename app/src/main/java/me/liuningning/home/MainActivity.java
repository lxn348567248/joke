package me.liuningning.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import me.liuningning.core.hotfix.HotFixManager;
import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.SkinActivity;
import me.liuningning.joke.R;

public class MainActivity extends SkinActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.id_main_msg)
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initView() {
        mMessageView.setText("注入数据");

    }

    @OnClick(R.id.id_load_dex)
    public void loadDex(View view) {
        File dexFile = new File(Environment.getExternalStorageDirectory(), "hotfix.dex");
        HotFixManager.getInstance().load(dexFile.getAbsolutePath());
    }


    @OnClick(R.id.id_load_dex)
    public void openHotFix(View view) {
        Intent intent = new Intent(this, HotFixActivity.class);
        startActivity(intent);
    }


    @Override
    protected void initData() {

    }
}
