package me.liuningning.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import me.liuningning.core.dialog.CommonDialog;
import me.liuningning.core.hotfix.HotFixManager;
import me.liuningning.core.http.HttpUtils;
import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.HttpCallBack;
import me.liuningning.framework.SkinActivity;
import me.liuningning.framework.navigator.DefaultNavigator;
import me.liuningning.joke.R;
import me.liuningning.mode.DiscoverListResult;

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
        new DefaultNavigator.Builder(this, null).setTitle("首页")
                .setRight("发布").build();

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


    @OnClick(R.id.id_main_show)
    public void openHotFix(View view) {
        Intent intent = new Intent(this, HotFixActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.id_show_comment)
    public void showComment(View view) {
        Log.e(TAG, "showComment");
        CommonDialog dialog = new CommonDialog.Build(this)
                .setContentView(R.layout.detail_comment_dialog)
                .fullWith()
                .fromBottom(true)
                .create();
        dialog.show();
    }


    @OnClick(R.id.id_load_data)
    public void loadData(View view) {
        HttpUtils.with(this).url("http://is.snssdk.com/2/essay/discovery/v3/")
                .addParameter("iid", "6152551759")
                .addParameter("aid", "7").execute(new HttpCallBack<DiscoverListResult>() {
            @Override
            public void onError(Exception e) {

                e.printStackTrace();
            }

            @Override
            public void onSuccess(DiscoverListResult result) {
                // String --> 对象   转换成可操作的对象
                // 显示列表
                Log.e("TAG", "name --> " + result.getData().getCategories().getName());

            }
        });
    }


    @Override
    protected void initData() {

    }
}
