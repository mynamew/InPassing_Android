package timi.inpassing_android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import timi.inpassing_android.R;


/**
 * 自定义的圆角imagebiew
 * @autor timi
 * create at 2017/5/25 11:39 
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {


    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {10,10, 10, 10, 10,10,10,10};

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);//TypedArray是一个数组容器
        rids[0]=   a.getDimension(R.styleable.RoundImageView_roundLeftTop, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[1]=   a.getDimension(R.styleable.RoundImageView_roundLeftTop, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[2]=   a.getDimension(R.styleable.RoundImageView_roundRightTop, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[3]=   a.getDimension(R.styleable.RoundImageView_roundRightTop, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[4]=  a.getDimension(R.styleable.RoundImageView_roundRightBottom, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[5]=  a.getDimension(R.styleable.RoundImageView_roundRightBottom, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[6]=   a.getDimension(R.styleable.RoundImageView_roundLeftBottom, 10);//防止在XML文件里没有定义，就加上了默认值10
        rids[7]=   a.getDimension(R.styleable.RoundImageView_roundLeftBottom, 10);//防止在XML文件里没有定义，就加上了默认值10
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 画图
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}