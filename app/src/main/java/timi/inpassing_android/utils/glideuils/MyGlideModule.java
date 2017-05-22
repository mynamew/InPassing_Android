package timi.inpassing_android.utils.glideuils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide 设置缓存
 *
 * @autor timi
 * create at 2017/5/2 9:21
 */
public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        //设置缓存大小   10m
//        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
//        //设置缓存池大小  20m
//        builder.setBitmapPool(new LruBitmapPool(20 * 1024 * 1024));
        //获取内存计算器
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        //获取Glide默认内存缓存大小
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //获取Glide默认图片池大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //将数值修改为之前的0.1倍
        int myMemoryCacheSize = (int) (0.1 * defaultMemoryCacheSize);
        int myBitmapPoolSize = (int) (0.1 * defaultBitmapPoolSize);
        //修改默认值
        builder.setMemoryCache(new LruResourceCache(myMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(myBitmapPoolSize));
        //设置磁盘缓存

        //设置磁盘缓存大小
        int size = 100 * 1024 * 1024;
        //设置磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, size));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
