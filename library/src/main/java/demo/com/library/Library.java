package demo.com.library;

import android.content.Context;

public class Library {
    public static Context context = null;
    public static void init(Context context){
        Library.context = context;
    }
    public static Context getContext(){
        if(context == null){
            throw new RuntimeException("Library is not initialized");
        }
        return context;
    }
}
