package me.liuningning.joke;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.liuningning.core.ioc.Injects;
import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.SkinActivity;

public class MainActivity extends SkinActivity {


    @ViewInject(R.id.id_main_msg)
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
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

    @Override
    protected void initData() {

    }


    @OnClick(R.id.id_main_show)
    public void onShow(View view) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }
}
