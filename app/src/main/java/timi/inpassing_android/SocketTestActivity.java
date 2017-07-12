package timi.inpassing_android;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import timi.inpassing_android.base.SuperActivity;
import timi.inpassing_android.config.Deployment;
import timi.inpassing_android.socket.SocketIO;

public class SocketTestActivity extends SuperActivity {


    @Override
    public int setLayoutId() {
        return R.layout.activity_socket_test;
    }

    @Override
    public void initBundle(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        SocketIO.setContext(this);
        SocketIO.setConnectListener(new SocketIO.OnConnectListener() {
            @Override
            public void onSuccess() {
                //链接成功 30s 后发送心跳
                SocketIO.heartSocket();
                SocketIO.loginSocket();
            }

            @Override
            public void onFailture(String reason) {

            }

            @Override
            public void onClose() {
            }
        });
        SocketIO.setReceiveListener(new SocketIO.OnReceiveListener() {
            @Override
            public void onReceiveSuccess(JSONObject js) {
                Logger.d("socket返回的数据--->"+ js.toString());
                try {
                    int code = js.getInt("ResultCode");
                    switch (code) {
                        case 200007://心跳验证成功
                            break;
                        case 200003://强制下线  互踢


                            break;
                        case 200006://刷新
                        case 200011://有降价商品购买未付款 已回降价区

                            break;
                        case 200010://有降价商品被购买

                            break;
                        case 200012://更新飘窗消息成功

                            break;
                        case 200013://商品价格更新

                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveFailture(String body, String reason) {

            }
        });
        SocketIO.connect(Deployment.SOCKET_SERVER, Integer.parseInt(Deployment.SOCKET_PORT), true);
    }
}
