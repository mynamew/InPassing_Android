package timi.inpassing_android.connection.soap;

import android.app.Activity;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Iterator;
import java.util.Map;

import timi.inpassing_android.config.Gateway;


/**
 * Created by master on 2016/7/8.
 */
public class WebService
{
    final static String namespace = "http://api.huanbay.com/";
    public interface WebServiceCallback
    {
        void onSuccess(JSONObject js);
        void onFailture(String msg);
    }
    static public void Remote(Activity act, Map<String, Object> arg, int md_int, int url_int, final WebServiceCallback callback)
    {
        final String url = Gateway.getServerURL(url_int);
        final String method = Gateway.getServerMethod(md_int);
        Remote(act, arg, method, url, callback);
    }
    static public void Remote(final Activity act, Map<String, Object> arg, final String method, String url, final WebServiceCallback callback)
    {
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        SoapObject soapObject = new SoapObject(namespace, method);
        if (arg != null) {
            Iterator<Map.Entry<String, Object>> it = arg.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = true;
        httpTransportSE.debug = true;

        ThreadPool.addTask(new Runnable() {

            @Override
            public void run() {
                SoapObject resultSoapObject = null;
                try {
                    httpTransportSE.call(namespace + method, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                        SoapPrimitive first = (SoapPrimitive)  resultSoapObject.getProperty(0);
//                        MTLog.e("SoapPrimitive---->", first.toString());
                        final JSONObject js = new JSONObject(first.toString());
                        if(callback!=null) {
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(js);
                                }
                            });
                        }
                    }
                } catch (final Exception e) {
                    if(callback!=null) {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailture(
                                        e.getMessage());
                            }
                        });
                    }
                }
            }

        });
    }

}
