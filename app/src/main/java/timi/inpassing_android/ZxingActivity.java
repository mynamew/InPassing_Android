package timi.inpassing_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import timi.inpassing_android.base.SuperActivity;
import timi.inpassing_android.utils.ToastUtils;
import zxing.activity.CaptureActivity;

public class ZxingActivity extends SuperActivity {
    private final int GO_TO_CAPTURE = 1001;
    @Override
    public int setLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    public void initBundle(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 二维码扫描
     * @param view
     */
    public void scanZxing(View view) {
        Intent intent = new Intent(ZxingActivity.this, CaptureActivity.class);
        startActivityForResult(intent, GO_TO_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {//扫码返回不为空
            String scan = data.getStringExtra("scan");
            Logger.d("二维码的扫描结果是-->"+scan);
            ToastUtils.showShort(ZxingActivity.this,"二维码的扫描结果是-->"+scan);
        }
    }
}
