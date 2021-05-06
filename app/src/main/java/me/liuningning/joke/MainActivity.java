package me.liuningning.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.liuningning.core.ioc.Injects;
import me.liuningning.core.ioc.annoation.OnClick;
import me.liuningning.core.ioc.annoation.ViewInject;

public class MainActivity extends AppCompatActivity {


    @ViewInject(R.id.id_main_msg)
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injects.inject(this);
        mMessageView.setText("注入数据");
    }


    @OnClick(R.id.id_main_show)
    public void onShow(View view) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }
}
