package timi.inpassing_android.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import timi.inpassing_android.R;


/**
 * 用来显示 loading  empty  error nodata等的数据界面
 *
 * @author timi.
 * @date 2017/3/14
 * @time 15:49
 */
public class StatusLayout extends FrameLayout {
    /**
     * loading 加载id
     */
    public static final int LAYOUT_LOADING_ID = 1;

    /**
     * 内容id
     */
    public static final int LAYOUT_CONTENT_ID = 2;

    /**
     * 异常id
     */
    public static final int LAYOUT_ERROR_ID = 3;

    /**
     * 网络异常id
     */
    public static final int LAYOUT_NETWORK_ERROR_ID = 4;

    /**
     * 空数据id
     */
    public static final int LAYOUT_EMPTYDATA_ID = 5;
    /**
     * 存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray();


    private int nodata_layoutid;
    private int nodinternet_layoutid;
    private int loading_layoutid;
    private Context mContext;
    //无网络的viewstub
    private ViewStub netWorkErrorVs;
    private ViewStub loadingVs;
    private ViewStub emptyVs;
    private ViewStub errorVs;
    private RetryClickListener clickListener;
    //无数据 添加data
    private RetryDataClickListener dataClickListener;
    //无数据点击的id
    private int retryDataId = 0;

    //loading的viestub
    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.status_layout, defStyleAttr, 0);

        nodata_layoutid = a.getResourceId(R.styleable.status_layout_nodata, 0);
        nodinternet_layoutid = a.getResourceId(R.styleable.status_layout_nointernet, 0);
        loading_layoutid = a.getResourceId(R.styleable.status_layout_loading, 0);
        //未设置 无数据时的数据，抛出异常
        if (nodata_layoutid == 0) {
            try {
                throw new Exception("Need a nodata layoutId");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //未设置 无网络的布局id
        if (nodinternet_layoutid == 0) {
            nodinternet_layoutid = R.layout.layout_no_internet;
        }
        //未设置 加载中的布局id
        if (loading_layoutid == 0) {
            loading_layoutid = R.layout.layout_loading;
        }
        a.recycle();
        this.mContext = context;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //居中
        params.gravity = Gravity.CENTER;
        this.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        hideContent(false);
        super.onFinishInflate();
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        if (inflateLayout(LAYOUT_LOADING_ID))
            showHideViewById(LAYOUT_LOADING_ID);
    }

    /**
     * 显示内容
     */
    public void showContent() {
        showHideViewById(0);
    }

    /**
     * 是否隐藏内容
     *
     * @param isVisible
     */
    @SuppressLint("WrongConstant")
    public void hideContent(boolean isVisible) {
//        MTLog.e("子view的数量", getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.setVisibility(isVisible ? VISIBLE : GONE);
        }
    }

    /**
     * 显示空数据
     */
    public void showEmptyData() {
        if (inflateLayout(LAYOUT_EMPTYDATA_ID))
            showHideViewById(LAYOUT_EMPTYDATA_ID);
    }

    /**
     * 显示网络异常
     */
    public void showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID))
            showHideViewById(LAYOUT_NETWORK_ERROR_ID);
    }


    /**
     * 根据ID显示隐藏布局
     *
     * @param id
     */
    @SuppressLint("WrongConstant")
    private void showHideViewById(int id) {
        //是否显示内容区域
        if (id != 0) {
            hideContent(false);
        } else {
            hideContent(true);
        }
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);
            //显示该view
            if (key == id) {
                valueView.setVisibility(View.VISIBLE);
            } else {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 实例化viewstub
     *
     * @param id
     * @return
     */
    private boolean inflateLayout(int id) {
        boolean isShow = true;
        if (layoutSparseArray.get(id) != null) return isShow;
        switch (id) {
            case LAYOUT_NETWORK_ERROR_ID:
                if (netWorkErrorVs == null) {
                    netWorkErrorVs = new ViewStub(mContext, nodinternet_layoutid);
                    this.addView(netWorkErrorVs);
                    View view = netWorkErrorVs.inflate();
                    layoutSparseArray.put(id, view);
                    view.findViewById(R.id.tv_repeat_load).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickListener.retryClick();
                        }
                    });
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_ERROR_ID:
                if (errorVs == null) {
                    View view = errorVs.inflate();
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_LOADING_ID:
                if (loadingVs == null) {
                    loadingVs = new ViewStub(mContext, loading_layoutid);
                    this.addView(loadingVs);
                    View view = loadingVs.inflate();
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_EMPTYDATA_ID:
                if (emptyVs == null) {
                    emptyVs = new ViewStub(mContext, nodata_layoutid);
                    this.addView(emptyVs);
                    View view = emptyVs.inflate();
                    //设置点击事件
                    if (retryDataId != 0) {
                        view.findViewById(retryDataId).setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dataClickListener.retryDataClick();
                            }
                        });
                    }
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
        }
        return isShow;
    }

    /**
     * 获取数据的view  为了没数据view的内容的初始化以及自己定义各种事件
     *
     * @return
     */
    public View getEmptyaView() {
        return layoutSparseArray.get(LAYOUT_EMPTYDATA_ID);
    }

    /**
     * 设置 无数据中的点击事件
     *
     * @param retryId
     * @param listener
     */
    public void setNodataClickListener(int retryId, final RetryDataClickListener listener) {
        this.retryDataId = retryId;
        this.dataClickListener = listener;
    }

    /**
     * 设置 监听事件
     *
     * @param clickListener
     */
    public void setRetyrListener(RetryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 没有网络重新加载的监听事件
     */
    public interface RetryClickListener {
        void retryClick();
    }

    /**
     * 没有数据的点击监听事件
     */
    public interface RetryDataClickListener {
        void retryDataClick();
    }
}
