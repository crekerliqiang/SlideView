package demo.com.library.utils;

import android.graphics.Rect;
import android.util.Log;

public class LLog {
    public static final String C_TAG = "common_tag";
    public static void d(String TAG,String msg){
        Log.d(TAG,msg);
    }
    public static void d(String TAG, Rect msg){
        Log.d(TAG,"left : " + msg.left + " top : " + msg.top + " right : " + msg.right + " bottom : " + msg.bottom);
    }
}

