package demo.com.library;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.widget.Toast;

public class Util {

    public static void toast(String s){
        Toast.makeText(Library.getmContext(),s,Toast.LENGTH_SHORT).show();
    }
    public static float dpToPixel(float dp) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
    public static float spToPixel(float sp){
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
    public static Bitmap getBitmap(int id){
        return BitmapFactory.decodeResource(Library.getmContext().getResources(),id);
    }
    public static Bitmap getBitmap(){
        return getBitmap(R.drawable.crekerli_doraemon);
    }
    public static Bitmap getBitmap(float width){
        return getBitmap(R.drawable.crekerli_doraemon,  (int)width);
    }
    public static Bitmap getBitmap(int id, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Library.getmContext().getResources(), id, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(Library.getmContext().getResources(),id, options);
    }

    /**
     * 对字符串做限制长度的裁剪
     * @param src 需要裁剪的字符串 e.g "这是一个非常非常非常非常非常非常非常非常非常长的字符串"
     * @param textSize 每一个字符的大小
     * @param mostSize 允许的最大大小，多于的将被剪掉
     *                 e.g. 被裁剪的字符串为："这是一个字符串" ; 每个字符串大小为5 ；允许最大大小为20 ；则该字符串只能保留前四个数字
     * @return
     */
    public static String cutText(final String src,final int textSize,final int mostSize){
        if(src == null || textSize <=0 || mostSize <=0 || textSize > mostSize)return null;
        char [] charStr = src.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < charStr.length;i++){
            if((i+1) * textSize < mostSize){
                stringBuilder.append(charStr[i]);
            }else{
                stringBuilder.append("...");
                return stringBuilder.toString();
            }
        }
        return null;
    }

}
