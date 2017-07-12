package timi.inpassing_android.connection.soap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by master on 2016/7/8.
 * 线程池
 */
public class ThreadPool
{
    static private ExecutorService service;
    static public void addTask(Runnable runnable)
    {
        getService().submit(runnable);
    }
    static private ExecutorService getService()
    {
        if(service==null){
            int num = Runtime.getRuntime().availableProcessors();
            service = Executors.newFixedThreadPool(num * 2);
        }
        return service;
    }

}
