package timi.inpassing_android.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
/** 
 * Toast 的工具类
 * @autor timi
 * create at 2017/5/22 13:48 
 */
public class ToastUtils {
	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {

		public void run() {

			mToast.cancel();

		}

	};

	public static void showLong(Context context, Object o) {
		  if (mToast == null) {
			  mToast = Toast.makeText(context, o.toString(), Toast.LENGTH_LONG);

	        } else {
	        	mToast.setText( o.toString());
	        }
		  mToast.show();
	}

	public static void showShort(Context context, Object o) {
		  if (mToast == null) {
			  mToast = Toast.makeText(context, o.toString(), Toast.LENGTH_SHORT);

	        } else {
	        	mToast.setText( o.toString());
	        }
		  mToast.show();
	}

	public static void showToast(Context mContext, int resId, int duration) {

		showToast(mContext, mContext.getResources().getString(resId), duration);
	}

	public static void showToast(Context mContext, String text, int duration) {

		mHandler.removeCallbacks(r);

		if (mToast != null)

			mToast.setText(text);

		else

			mToast = Toast.makeText(mContext, text, duration);

		mHandler.postDelayed(r, duration);

		mToast.show();

	}
}
