package demo.com.library.utils;

import android.graphics.Rect;
import android.util.Log;

public class LLog {
    private static final String C_TAG = "library_common_tag";

    public static void d(String TAG,String msg){
        Log.d(TAG,msg);
    }
    public static void d(String msg){
        d(C_TAG,msg);
    }


    public static void d(String TAG, Rect msg){
        Log.d(TAG,"left : " + msg.left + " top : " + msg.top + " right : " + msg.right + " bottom : " + msg.bottom);
    }
    public static void d(Rect msg){
        d(C_TAG,"left : " + msg.left + " top : " + msg.top + " right : " + msg.right + " bottom : " + msg.bottom);
    }


    public static void e(String TAG,String msg){
        Log.e(TAG,msg);
    }
    public static void e(String msg){
        e(C_TAG,msg);
    }


}

