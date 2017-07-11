package timi.inpassing_android;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.library.viewspread.helper.BaseViewHelper;
import com.orhanobut.logger.Logger;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import timi.inpassing_android.base.SuperActivity;
import timi.inpassing_android.views.CustomFloatButton;
import timi.inpassing_android.widgets.MyCustomFloatButton;

public class HomeActivity extends SuperActivity implements TencentLocationListener {
    //地图
    private MapView mapview;
    //侧滑的菜单
    private SlidingMenu menu;
    //地图
    private TencentMap tencentMap;
    //定位监听
    private TencentLocationManager locationManager;
    //定位的请求
    private TencentLocationRequest request;
    //当前的经纬度
    private double latitude, longitude;
    //当前的坐标
    private LatLng latLngCurrentLocation;

    //view
    private MyCustomFloatButton btFloat;

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initBundle(Bundle savedInstanceState) {
        //地图
        mapview = (MapView) findViewById(R.id.mapview);
        mapview.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        //侧滑菜单
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置菜单占屏幕的比例
        menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 3);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);
        menu.setOffsetFadeDegree(0.4f);
        btFloat = (MyCustomFloatButton) findViewById(R.id.bt_home_publish_order);
        //设置map
        //获取TencentMap实例
        tencentMap = mapview.getMap();
        //设置中心点
        tencentMap.setCenter(new LatLng(39, 116));
        //设置缩放级别
        tencentMap.setZoom(16);
        ((MyCustomFloatButton) findViewById(R.id.bt_home_publish_order)).setCustonOnClickListener(new MyCustomFloatButton.MyCustomButtonClickListener() {
            @Override
            public void click(View view) {
                Intent intent = new Intent(HomeActivity.this, PublishOrderActivity.class);
                intent.putExtra("id",view.getId());
                new BaseViewHelper
                        .Builder(HomeActivity.this, view)
                        .startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        //设置定位时间间隔
        request.setInterval(1000);
        locationManager = TencentLocationManager.getInstance(HomeActivity.this);
        int error = locationManager.requestLocationUpdates(request, this);
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        super.onStop();
    }

    /**
     * 打开策划的菜单
     *
     * @param view
     */
    public void openSlideMenu(View view) {
        new CustomFloatButton.Builder().setTitle("接单").setImgRes(R.drawable.bg_custom_float_bt).setContext(this).build().show();
        menu.showMenu();
    }

    /**
     * 重新定位
     *
     * @param view
     */
    public void reloadLocation(View view) {
        //设置定位的监听器
        locationManager.requestLocationUpdates(request, this);
    }

    /**
     * 定位改变
     *
     * @param tencentLocation
     * @param error
     * @param s
     */
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
        Logger.d(TAG+"定位---->",tencentLocation.getLatitude()+":"+tencentLocation.getLongitude());
        if (TencentLocation.ERROR_OK == error) {
            // 定位成功 设置经纬度
            latitude = tencentLocation.getLatitude();
            longitude = tencentLocation.getLongitude();
            //设置当前位置的经纬度
            latLngCurrentLocation = new LatLng(latitude, longitude);
            //设置地图的中心点
            tencentMap.setCenter(latLngCurrentLocation);
            //移除监听器
            locationManager.removeUpdates(this);
            //设置    marker
            Marker marker = tencentMap.addMarker(new MarkerOptions()
                    .position(latLngCurrentLocation)
                    .title("当前位置")
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker())
                    .draggable(true));
            marker.showInfoWindow();// 设置默认显示一个infoWindow
        } else {
            // 定位失败
            //设置当前位置的经纬度
            latLngCurrentLocation = new LatLng(43, 64);
        }
    }

    /**
     * 状态改变
     *
     * @param s
     * @param i
     * @param s1
     */
    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

}
