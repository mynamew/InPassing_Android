package timi.inpassing_android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import timi.inpassing_android.base.SuperActivity;

public class MainActivity extends SuperActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initBundle(Bundle savedInstanceState) {

    }


    @Override
    public void initView() {
        tvTest.setText("heihei ");

    }

    @Override
    public void initData() {

    }

    public void openSlideMenu(View view) {
    }
}
