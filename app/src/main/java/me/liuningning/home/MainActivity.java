package me.liuningning.home;


import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.SkinActivity;
import me.liuningning.framework.db.DaoFactory;
import me.liuningning.framework.skin.SkinManager;
import me.liuningning.joke.R;

public class MainActivity extends SkinActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.id_main_msg)
    private TextView mMessageView;

    @ViewInject(R.id.ic_load_image)
    private ImageView mIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoFactory.getInstance().init(this);

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


    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.id_main_load_skin)
    public void loadSkin(View view) {

        String skinPath = Environment.getExternalStorageDirectory() + File.separator + "red.skin";
        SkinManager.getInstance().loadSkin(skinPath);


    }


}
