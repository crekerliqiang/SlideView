package demo.com.library;

import android.content.Context;

public class Library {
    public static Context mContext = null;
    public static void init(Context context){
        mContext = context;
    }
    public static Context getmContext(){
        if(mContext == null){
            throw new RuntimeException("Library is not initialized");
        }
        return mContext;
    }
}
