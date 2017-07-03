package timi.inpassing_android;



import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import timi.inpassing_android.base.SuperActivity;

public class HomeActivity extends SuperActivity {


    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initBundle() {

    }

    @Override
    public void initView() {
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置菜单占屏幕的比例
        menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 4);

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
    }

    @Override
    public void initData() {

    }
}
