package demo.com.library.utils;

import android.graphics.Color;

public class Constants {
    //
    /**
     * 文字中x y的偏移量
     */
    public static final int TEXT_OFFSET_X = (int) Util.dpToPixel(1);
    public static final int TEXT_OFFSET_Y = (int)Util.dpToPixel(5);
    /**
     * 文字滑动的阈值
     * 从右往左滑动时，scaleRatioX 的范围是 1--0 ；当 scaleRatioX 小于该阈值时，开启动画
     */
    public static final float SCALE_RATIO_LEFT_X_THRESHOLD = 0.999f;
    public static final float SCALE_RATIO_RIGHT_X_THRESHOLD = 0.001f;
    /**
     * Image:图片默认宽度[正方形]
     */
    public static final int IMAGE_SLIDE_LEN_DEFAULT = (int)Util.dpToPixel(60);
    /**
     * Image:图片的默认偏移
     */
    public static final int IMAGE_MARGIN_START_DEFAULT = (int)Util.spToPixel(10);
    /**
     * title : message 的高度的最大比例[不能超出]
     */
    public static final float TEXT_MAX_RATIO = 0.7f;
    /**
     * Title text 默认颜色
     */
    public static final int TEXT_COLOR_DEFAULT = Color.BLACK;
    /**
     * Title text 默认值
     */
    public static final String TITLE_TEXT_DEFAULT = "";
    /**
     * Title text 默认大小
     */
    public static final int TITLE_SIZE_DEFAULT = (int)Util.spToPixel(24);
    /**
     * Title text 默认起始偏移
     */
    public static final int TITLE_MARGIN_START_DEFAULT = (int)Util.dpToPixel(10);
    /**
     * Title 文字的高度占整个View的比例
     */
    public static final float TITLE_HEIGHT = 0.75f;

    /**
     * Message text 默认值
     */
    public static final String MESSAGE_TEXT_DEFAULT = "";
    /**
     * Message text 默认大小
     */
    public static final int MESSAGE_SIZE_DEFAULT = (int)Util.spToPixel(12);
    /**
     * Message text 默认起始偏移
     */
    public static final int MESSAGE_MARGIN_START_DEFAULT = (int)Util.dpToPixel(10);

    /**
     * Menu 背景默认宽高比
     */
    public static final float MENU_BACKGROUND_ASPECT_DEFAULT = 1.0f;
    /**
     * Menu的默认字体大小
     */
    public static final int MENU_TEXT_SIZE_DEFAULT = (int)Util.spToPixel(20f);

    /**
     * Menu A 的默认背景色
     */
    public static final int MENU_A_BACKGROUND_DEFAULT = Color.RED;

    /**
     * Menu B 的默认背景色
     */
    public static final int MENU_B_BACKGROUND_DEFAULT = Color.GRAY;

    /**
     * Click Listener 存储背景坐标起始位置的HaspMap中的键
     */
    public static final String KEY_X_START = "kye_x_start";
    public static final String KEY_X_END = "kye_x_end";

    /**
     * 菜单的询问字符串
     */
}
