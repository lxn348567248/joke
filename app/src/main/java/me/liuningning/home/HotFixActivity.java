package me.liuningning.home;


import android.view.View;
import android.widget.Toast;

import me.liuningning.framework.SkinActivity;
import me.liuningning.joke.R;

public class HotFixActivity extends SkinActivity {


    @Override
    protected int layoutId() {
        return R.layout.activity_hot_fix;
    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initView() {

        findViewById(R.id.id_hotfix_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 2 / 0;
                Toast.makeText(HotFixActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initData() {

    }
}
