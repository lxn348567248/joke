package me.liuningning.joke;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.SkinActivity;

public class MainActivity extends SkinActivity {


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

        findViewById(R.id.id_main_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("","path is ..............");
                int a = 2 / 0;
            }
        });
    }

    @Override
    protected void initView() {
        mMessageView.setText("注入数据");
    }

    @Override
    protected void initData() {

    }



}
