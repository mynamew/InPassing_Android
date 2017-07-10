package timi.inpassing_android.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.lang.reflect.Field;

import timi.inpassing_android.utils.statusutils.StatusBarUtil;

/**
 * 自定义的浮动按钮
 *
 * @autor timi
 * create at 2017/7/6 9:03
 */

public class MyCustomFloatButton extends AppCompatButton {
    private float downX, downY;  //按下的坐标
    private float moveX, moveY;  //移动的坐标
    private int mScreenWidth, mScreenHeight;
    private Context mContext;
    //按下的x
    private float moveDownX, moveDownY;
    //执行动画前 按钮原来的位置
   private int[] lastPosition=new int[2];
   private int[] animaEndPosition=new int[]{mScreenWidth/2-getWidth()/2,mScreenHeight/3-getHeight()/2};
    public MyCustomFloatButton(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //获取宽高
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;

    }

    public MyCustomFloatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCustomFloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    /**
     * 监听touch 事件 让按钮跟随手指滑动
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //按下
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            downX = event.getRawX();
            downY = event.getRawY();
            moveDownX = event.getRawX();
            moveDownY = event.getRawY();
        }
        //移动
        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            moveX = event.getRawX();
            moveY = event.getRawY();
            this.setX(getX() + (moveX - downX));
            this.setY(getY() + (moveY - downY));
            downX = event.getRawX();
            downY = event.getRawY();
        }
        //松开 抬起
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //判断是否是 点击事件
            int tempDX = (int) ((int) event.getRawX() - moveDownX);
            int tempDY = (int) ((int) event.getRawY() - moveDownY);
            if (Math.abs(tempDX) < 10 && Math.abs(tempDY) < 10) {//如果移动距离很小 则当作点击事件处理
                try {
                    //反射 触发点击事件
                    Field field = View.class.getDeclaredField("mListenerInfo");
                    field.setAccessible(true);
                    Object object = field.get(this);
                    field = object.getClass().getDeclaredField("mOnClickListener");
                    field.setAccessible(true);
                    object = field.get(object);
                    if (object != null && object instanceof View.OnClickListener) {
                        ((View.OnClickListener) object).onClick(this);
                    }
                } catch (Exception e) {

                }
            } else {
                //如果是正常的按下 移动 抬起则走下面的流程
                if (event.getRawX() > mScreenWidth / 2) {
                    this.setX(mScreenWidth - (this.getWidth()));
                } else {
                    this.setX(0);
                }
                //Y轴偏移量
                float distance=0;
                Log.d("控件的坐标--->","getY->"+getY());
                Log.d("getTranslationY--->","getTranslationY->"+getTranslationY());
                Log.d("控件的高度--->","getHeight->"+getHeight());
                Log.d("getTop--->","getTop->"+getTop());
                Log.d("mScreenHeight--->","mScreenHeight->"+mScreenHeight);
                Log.d("状态栏的高度--->","->"+ StatusBarUtil.getStatusBarHeight(mContext)+"");
                if((getY()+getHeight()+getTop()+StatusBarUtil.getStatusBarHeight(mContext))>=mScreenHeight){
                    distance=mScreenHeight-getHeight()*2-getHeight()/2;
                }else if((getY())<=0){
                    distance=0;
                }else{
                    distance=getY();
                }
                this.setY(distance);
            }
        }
        return true;
    }



    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setCustonOnClickListener(final MyCustomButtonClickListener listener) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮 执行动画效果
                //获取当前位置
                MyCustomFloatButton.this.getLocationInWindow(lastPosition);
                listener.click(v);
            }
        });
    }


    public interface MyCustomButtonClickListener {
        void click(View view);
    }
}
