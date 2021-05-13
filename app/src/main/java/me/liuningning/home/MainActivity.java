package me.liuningning.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;
import me.liuningning.framework.SkinActivity;
import me.liuningning.framework.db.DaoFactory;
import me.liuningning.framework.db.IDaoSupport;
import me.liuningning.framework.navigator.DefaultNavigator;
import me.liuningning.joke.R;

public class MainActivity extends SkinActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.id_main_msg)
    private TextView mMessageView;

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
        new DefaultNavigator.Builder(this, null).setTitle("首页")
                .setRight("发布").build();

    }

    @Override
    protected void initView() {
        mMessageView.setText("注入数据");

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.id_main_show)
    public void openHotFix(View view) {
        Intent intent = new Intent(this, HotFixActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.id_main_insert)
    public void insertData(View view) {
        IDaoSupport<Person> dao = DaoFactory.getInstance().getDao(Person.class);
        dao.insert(new Person("love", 32, 167));

    }

    @OnClick(R.id.id_main_update)
    public void updateData(View view) {
        IDaoSupport<Person> dao = DaoFactory.getInstance().getDao(Person.class);
        dao.update(new Person("plmm", 35, 167), "id=?", "1");
    }

    @OnClick(R.id.id_main_delete)
    public void deleteData(View view) {
        IDaoSupport<Person> dao = DaoFactory.getInstance().getDao(Person.class);
        dao.delete("id=?", "1");
    }


    @OnClick(R.id.id_main_query)
    public void queryData(View view) {

        IDaoSupport<Person> dao = DaoFactory.getInstance().getDao(Person.class);
        List<Person> personList = dao.getQuerySupport().query();
        Log.d("TAG", personList.toString());
    }


}
