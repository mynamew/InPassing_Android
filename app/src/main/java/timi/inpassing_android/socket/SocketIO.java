package timi.inpassing_android.socket;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/** 
 * socket的使用
 * @autor timi
 * create at 2017/7/12 11:18 
 */

public class SocketIO {
    //心跳的时间
    private static final int HEART_TIME = 30000;

    public interface OnConnectListener {
        void onSuccess();

        void onFailture(String reason);

        void onClose();
    }

    public interface OnReceiveListener {
        void onReceiveSuccess(JSONObject js);

        void onReceiveFailture(String body, String reason);
    }

    static private OnReceiveListener receiveListener = null;
    static private OnConnectListener connectListener = null;
    static private AsyncSocket socket;
    static private String host = null;
    static private int port = 0;
    static private boolean autoConnect;
    static private boolean isActive = false;


    static public void sendMessage(String body) {
        if (isActive()) socket.write(new ByteBufferList(body.getBytes()));
        else Log.d("MT", "sendMessage Failture ==> There is no socket connection!!!");
    }

    static public void close() {
        autoConnect = false;
        if (socket != null) socket.close();
        socket = null;
    }

    static public void dispose() {
        close();
        receiveListener = null;
        connectListener = null;
    }

    static public synchronized void connect(final String hostx, final int portx, boolean autocon) {
        if (isActive()) {
            return;
        }
        Logger.d("SOcketIO connect");
        host = hostx;
        port = portx;
        autoConnect = autocon;
        isActive = true;

        //关闭自动重连
        if (null != conTimer) {
            conTimer.cancel();
            conTimer = null;
        }

        AsyncServer.getDefault().connectSocket(host, port, new com.koushikdutta.async.callback.ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socketx) {
                if (ex != null) {
                    if (connectListener != null) {
                        isActive = false;
                        connectListener.onFailture(ex.getMessage());
                    }
                    onReconnect();
                    Logger.d("SOcketIO onReconnect");
                } else {
                    socket = socketx;
                    onConnectHD();
                    if (connectListener != null) {
                        isActive = true;
                        connectListener.onSuccess();
                    }
                    Logger.d("SOcketIO onSuccess");
                }
            }
        });
    }

    static private void onConnectHD() {
        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                String body = new String(bb.getAllByteArray());
                try {
                    JSONObject js = new JSONObject(body);
                    if (receiveListener != null) receiveListener.onReceiveSuccess(js);
                } catch (JSONException e) {
                    if (receiveListener != null)
                        receiveListener.onReceiveFailture(body, e.getMessage());
                }
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                socket = null;
                isActive = false;
                if (connectListener != null) connectListener.onClose();
                onReconnect();
            }
        });
    }

    private static Timer conTimer;

    static private void onReconnect() {
        if (conTimer != null) {
            conTimer.cancel();
            conTimer = null;
        }
        //检查重连
        if (autoConnect && host != null && !host.equals("") && port > 0) {
            conTimer = new Timer();
            conTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    connect(host, port, true);
                }
            }, 1000 * 3);
            Log.d("MT", "Re-connect");
        } else {
            Log.d("MT", "No-connect");
        }
    }


    static public void setReceiveListener(OnReceiveListener listener) {
        receiveListener = listener;
    }

    static public void setConnectListener(OnConnectListener listener) {
        connectListener = listener;
    }

    static public boolean isActive() {
        return isActive;
    }

    //handler用来定时发送心跳
    static Handler handler = new Handler();
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            heartSocket();
        }
    };

    //心跳
    static public void heartSocket() {
        handler.postDelayed(runnable, HEART_TIME);
    }

    //断开连接
    static public void closeSocket() {
        handler.removeCallbacks(runnable);
        close();
    }

    //登录
    static public void loginSocket() {

    }

    //上下文
    private static Context mContext;

    static public void setContext(Context context) {
        mContext = context;
    }
}
