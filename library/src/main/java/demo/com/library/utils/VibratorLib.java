package demo.com.library.utils;

import android.app.Service;
import android.os.Vibrator;

public class VibratorLib {

    static Vibrator vibrator=(Vibrator)Library.getContext().getSystemService(Service.VIBRATOR_SERVICE);

    /**
     * 手机震动
     * @param time 时间
     */
    public static void vibrate(long time){
        vibrator.vibrate(time);
    }
}
