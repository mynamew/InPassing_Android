package timi.inpassing_android.utils.glideuils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

//import hello.master.com.circleimage.CircleImageView;

/**
 * 图片加载的工具类
 *
 * @autor renjie
 * create at 2017/4/28 16:43
 */

public class ImageLoaderUtis {
    private static ImageLoaderUtis instance = null;

    private ImageLoaderUtis() {

    }

    public static ImageLoaderUtis getInstance() {
        if (null == instance) {
            instance = new ImageLoaderUtis();
        }
        return instance;
    }

    /**
     * 显示图片
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     */
    public void showImageFromUrl(Context context, int errorimg, String url, ImageView imgeview, boolean isSkipMemoryCache) {
        Glide.with(context).load(url)// 加载图片
                .error(errorimg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .placeholder(errorimg)// 设置占位图
                .into(imgeview);


    }
    /**
     * 显示图片
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param file      本地Uri
     * @param imgeview 组件
     */
    public void showImageFromLocal(Context context, int errorimg, File file, ImageView imgeview, boolean isSkipMemoryCache) {
        Glide.with(context).load(file)// 加载图片
                .error(errorimg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .placeholder(errorimg)// 设置占位图
                .into(imgeview);


    }

    /**
     * 显示图片
     * 图片type fitcenter
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     */
    public void showImageFromUrlFitCenter(Context context, int errorimg, String url, ImageView imgeview, boolean isSkipMemoryCache) {
        Glide.with(context).load(url)// 加载图片
                .error(errorimg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .fitCenter()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .placeholder(errorimg)// 设置占位图
                .into(imgeview);

    }
//
//    /**
//     * 显示圆形头像CircleImageView
//     *
//     * @param context  上下文
//     * @param errorimg 错误的资源图片
//     * @param url      图片链接
//     * @param imgeview 组件
//     */
//    public void showImageFromUrl(Context context, int errorimg, String url, final CircleImageView imgeview, boolean isSkipMemoryCache) {
//        Glide.with(context).load(url)// 加载图片
//                .error(errorimg)// 设置错误图片
//                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
//                .centerCrop()
//                .placeholder(errorimg)// 设置占位图
//                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        imgeview.setImageDrawable(resource);
//                        return false;
//                    }
//                }).into(imgeview);
//
//    }

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     * @param listener 加载监听的回调
     */
    public void showImageFromUrl(Context context, int errorimg, String url, ImageView imgeview, boolean isSkipMemoryCache, RequestListener<String, GlideDrawable> listener) {
        Glide.with(context).load(url)// 加载图片
                .listener(listener)  //加载监听的回调
                .error(errorimg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .placeholder(errorimg)// 设置占位图
                .into(imgeview);

    }

    /**
     * 显示图片Imageview 获取图片的bitmap
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     */
    public void showImageFromUrlGetBitmap(Context context, int errorimg, String url, boolean isSkipMemoryCache, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url)// 加载图片
                .asBitmap()
                .error(errorimg)// 设置错误图片
                .centerCrop()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .placeholder(errorimg)// 设置占位图
                .into(simpleTarget);

    }

//    /**
//     * 显示图片Imageview
//     *
//     * @param context  上下文
//     * @param errorimg 错误的资源图片
//     * @param url      图片链接
//     * @param imgeview 组件
//     * @param width    height  宽度 高度
//     */
//    public void showImageFromUrlOverride(Context context, int errorimg, String url, int width, int height, final CircleImageView imgeview, boolean isSkipMemoryCache) {
//
//        Glide.with(context).load(url)// 加载图片
//                .error(errorimg)// 设置错误图片
//                .fitCenter()
//                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
//                .placeholder(errorimg)// 设置占位图
//                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
//                .override((int) context.getResources().getDimension(width), (int) context.getResources().getDimension(height))
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        imgeview.setImageDrawable(resource);
//                        return false;
//                    }
//                }).into(imgeview);
//    }

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     * @param width    height  宽度 高度
     */
    public void showImageFromUrlOverride(Context context, int errorimg, String url, int width, int height, final ImageView imgeview, boolean isSkipMemoryCache) {

        Glide.with(context).load(url)// 加载图片
                .asBitmap()
                .error(errorimg)// 设置错误图片
                .placeholder(errorimg)// 设置占位图
                .centerCrop()
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imgeview.setImageBitmap(resource);
                        return false;
                    }
                })
                .into((int) context.getResources().getDimension(width), (int) context.getResources().getDimension(height));

    }

    /**
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     * @param width    height  宽度 高度
     */
    public void showImageFromUrlOverride(Context context, int errorimg, String url, int width, int height, ImageView imgeview, boolean isSkipMemoryCache, RequestListener<String, GlideDrawable> listener) {
        Glide.with(context).load(url)// 加载图片
                .error(errorimg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .centerCrop()
                .listener(listener)  //加载监听的回调
                .placeholder(errorimg)// 设置占位图
                .skipMemoryCache(isSkipMemoryCache)//是否缓存到内存中
                .override(width, height)
                .into(imgeview);

    }

    /**
     * 显示图片 圆角显示  ImageView
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     */
    public void showImageViewFromUrlToCircle(Context context, int errorimg,
                                             String url, ImageView imgeview) {
        Glide.with(context).load(url)
                // 加载图片
                .error(errorimg)
                // 设置错误图片
                .crossFade()
                // 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(errorimg)
                .centerCrop()
                // 设置占位图
                .transform(new GlideCircleTransform(context))//圆角
                .into(imgeview);
    }

    /**
     * 加载动图
     *
     * @autor timi
     * create at 2017/5/4 11:15
     */
    public void showGifFromRes(Context context, int drawableId, ImageView imgeview) {
        Glide.with(context).load(drawableId).asGif().into(imgeview);
    }

    /**
     * 清理内存
     *
     * @autor timi
     * create at 2017/5/2 9:52
     */
    // 清除Glide内存缓存
    public static boolean clearCacheMemory(Application context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 清除磁盘缓存
     *
     * @autor timi
     * create at 2017/5/2 10:49
     */
    public static boolean clearDiskMemory(Application context) {
        try {
            return GlideCatchUtil.getInstance().clearCacheDiskSelf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
