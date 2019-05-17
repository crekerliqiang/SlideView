package demo.com.library.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import demo.com.library.LLog;
import demo.com.library.R;
import demo.com.library.Util;

import static demo.com.library.Constants.IMAGE_MARGIN_START_DEFAULT;
import static demo.com.library.Constants.IMAGE_SLIDE_LEN_DEFAULT;
import static demo.com.library.Constants.MESSAGE_MARGIN_START_DEFAULT;
import static demo.com.library.Constants.MESSAGE_SIZE_DEFAULT;
import static demo.com.library.Constants.MESSAGE_TEXT_DEFAULT;
import static demo.com.library.Constants.SCALE_RATIO_LEFT_X_THRESHOLD;
import static demo.com.library.Constants.SCALE_RATIO_RIGHT_X_THRESHOLD;
import static demo.com.library.Constants.TEXT_COLOR_DEFAULT;
import static demo.com.library.Constants.TEXT_MAX_RATIO;
import static demo.com.library.Constants.TEXT_OFFSET_X;
import static demo.com.library.Constants.TEXT_OFFSET_Y;
import static demo.com.library.Constants.TITLE_HEIGHT;
import static demo.com.library.Constants.TITLE_MARGIN_START_DEFAULT;
import static demo.com.library.Constants.TITLE_SIZE_DEFAULT;
import static demo.com.library.Constants.TITLE_TEXT_DEFAULT;


public class SlideView extends View {

    private static final String TAG = SlideView.class.getSimpleName();

    private Paint paintBackground3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintTitle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintMessage = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintMenu = new Paint(Paint.ANTI_ALIAS_FLAG);
    //触屏监听
    private MyGestureListener gestureListener;
    //触屏检测
    private GestureDetectorCompat detectorCompat;
    //滑动效果的动画
    ObjectAnimator animator = ObjectAnimator.ofFloat(this,"scaleRatioX",1f);
    boolean isAnimatorStart = false;
    //结束监听

    //水平方向的缩放比例
    float scaleRatioX = 1.0f;
    public float getScaleRatioX() {
        return scaleRatioX;
    }
    public void setScaleRatioX(float scaleRatioX) {
        this.scaleRatioX = scaleRatioX;
        invalidate();
    }
    //图片
    int imageSource;
    Bitmap bitmap;
    int imageSlideLength;
    float imageMarginStart;
    boolean isDrawBitmap;
    //Title
    String titleText;
    int titleTextSize;
    int titleTextColor;
    int titleTextMarginStart;
    int titleViewHeight;
    int titleTextOffsetX;
    int titleTextOffsetY;
    //Message
    String messageText;
    int messageTextSize;
    int messageTextColor;
    int messageTextMarginStart;
    int messageViewHeight;
    int messageTextOffsetX;
    int messageTextOffsetY;

    //滑动菜单区域
    /**
     * 菜单的文字 X 轴偏移大小
     */
    int menuTextOffsetX;
    /**
     * 菜单的文字 Y 轴偏移大小
     */
    int menuTextOffsetY;
    /**
     * 菜单的背景 X 轴偏移大小
     */
    int menuBackgroundOffsetX;
    /**
     * 菜单背景的宽度
     */
    int menuBackgroundWidth;
    /**
     * 菜单背景的高度
     */
    int menuBackgroundHeight;
    List<String> menuString = new ArrayList<>();
    List<Integer> menuColor = new ArrayList<>();
    public void putMenuString(List<String> menuString){
        this.menuString = menuString;
    }
    public void putMenuColor(List<Integer> menuColor){
        this.menuColor = menuColor;
    }

    public SlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取attrs 资源
        // 1.图片资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SlideView);
        imageSource = typedArray.getResourceId(R.styleable.SlideView_image_src,0);
        imageSlideLength = typedArray.getDimensionPixelSize(R.styleable.SlideView_image_slide_length,IMAGE_SLIDE_LEN_DEFAULT);
        imageMarginStart = typedArray.getDimensionPixelSize(R.styleable.SlideView_image_margin_start,IMAGE_MARGIN_START_DEFAULT);
        //图片为空的情况
        if(imageSource == 0){
            imageSource = R.drawable.crekerli_pig;
            isDrawBitmap = false;
        }else{
            isDrawBitmap = true;
        }
        bitmap = Util.getBitmap(imageSource,imageSlideLength);
        //2.Title
        titleText = typedArray.getString(R.styleable.SlideView_title_text);
        if(titleText == null)titleText = TITLE_TEXT_DEFAULT;
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.SlideView_title_text_size, TITLE_SIZE_DEFAULT);
        titleTextColor = typedArray.getColor(R.styleable.SlideView_title_text_color, TEXT_COLOR_DEFAULT);
        titleTextMarginStart = typedArray.getDimensionPixelSize(R.styleable.SlideView_title_text_margin_start,TITLE_MARGIN_START_DEFAULT);
        //3.Message
        messageText = typedArray.getString(R.styleable.SlideView_message_text);
        if(messageText == null)messageText = MESSAGE_TEXT_DEFAULT;
        messageTextSize = typedArray.getDimensionPixelSize(R.styleable.SlideView_message_text_size,MESSAGE_SIZE_DEFAULT);
        messageTextColor = typedArray.getColor(R.styleable.SlideView_message_text_color, TEXT_COLOR_DEFAULT);;
        messageTextMarginStart = typedArray.getDimensionPixelSize(R.styleable.SlideView_message_text_margin_start,MESSAGE_MARGIN_START_DEFAULT);;



        typedArray.recycle();
        //Title文字规则
        paintTitle.setColor(messageTextColor);
        paintTitle.setTextSize(titleTextSize);
        //Message文字规则
        paintMessage.setColor(titleTextColor);
        paintMessage.setTextSize(messageTextSize);
        //初始化点击监听
        gestureListener = new MyGestureListener();
        detectorCompat = new GestureDetectorCompat(context,gestureListener);
        //动画结束监听
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatorStart = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        titleViewHeight = (int)((float)getHeight() * TITLE_HEIGHT);
        messageViewHeight = getHeight() - titleViewHeight;
        //title x y 的坐标
        titleTextOffsetX = TEXT_OFFSET_X + bitmap.getWidth() + titleTextMarginStart;
        titleTextOffsetY = (titleViewHeight + titleTextSize)/2 - TEXT_OFFSET_Y;
        //对Title的文本长度做限制
        int mostTextSize = (int)((getWidth() - titleTextOffsetX)*TEXT_MAX_RATIO);
        if(titleTextSize * titleText.length() > mostTextSize){
            titleText = Util.cutText(titleText,titleTextSize,mostTextSize);
        }
        //message x y 的坐标
        messageTextOffsetX = TEXT_OFFSET_X + bitmap.getWidth() + messageTextMarginStart;
        messageTextOffsetY = getHeight() - (messageViewHeight - messageTextSize)/2 - TEXT_OFFSET_Y*2;
        //对Message的文本长度做限制
        mostTextSize = (int)((getWidth() - messageTextOffsetX)*TEXT_MAX_RATIO);
        if(messageTextSize * messageText.length() > mostTextSize){
            messageText = Util.cutText(messageText,messageTextSize,mostTextSize);
        }
        //菜单的背景宽度和高度 [正方形]
        menuBackgroundWidth = getHeight();
        menuBackgroundHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.scale(1 + scaleRatioX * 2, 1,0,getHeight()>>1);
        canvas.save();
        //移动
        canvas.translate(menuString.size() * menuBackgroundWidth * ( 1 * scaleRatioX - 1f),0);
        //绘制 image
        if(isDrawBitmap)canvas.drawBitmap(bitmap,imageMarginStart, (getHeight() - bitmap.getHeight())>>1, paintTitle);
        //绘制 title
        canvas.drawText(titleText,titleTextOffsetX ,titleTextOffsetY , paintTitle);
        //绘制message
        canvas.drawText(messageText,messageTextOffsetX,messageTextOffsetY,paintMessage);
        canvas.restore();
        //绘制菜单区域
        for(int i = 0;i < menuString.size();i++){
            canvas.scale(1 - scaleRatioX, 1,getWidth(),getHeight()>>1);
            //设置为正方形 所以长宽相等
            menuBackgroundOffsetX = getWidth() - menuBackgroundWidth * (i+1);
            paintBackground3.setColor(menuColor.get(i));
            canvas.drawRect(menuBackgroundOffsetX,0,menuBackgroundOffsetX + menuBackgroundWidth,menuBackgroundHeight,paintBackground3);
            paintBackground3.reset();

            int textSize = (int)Util.spToPixel(28);
            paintMenu.setTextSize(textSize);
            menuTextOffsetX = menuBackgroundOffsetX + (menuBackgroundWidth - menuString.get(i).length() * textSize)/2;
            menuTextOffsetY = (menuBackgroundHeight + textSize)/2;
            canvas.drawText(menuString.get(i), menuTextOffsetX + TEXT_OFFSET_X,
                    menuTextOffsetY - TEXT_OFFSET_Y, paintMenu);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detectorCompat.onTouchEvent(event);
    }

    public class MyGestureListener implements GestureDetector.OnGestureListener{

        public boolean onDown(MotionEvent e) {
            LLog.d(TAG,"down " + e.getX());
            //返回true 表示接收该事件
            return true;
        }

        public void onShowPress(MotionEvent e) {

        }

        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        //滑动
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float s = scaleRatioX;
            scaleRatioX = scaleRatioX - distanceX/getWidth();
            if(scaleRatioX < 0f)scaleRatioX = 0f;
            if(scaleRatioX > 1f)scaleRatioX = 1f;
            LLog.d(TAG,"dx " + distanceX + " scale " + s + ":" + scaleRatioX );

            //1.(scaleX > hold && 右滑) --> 动画：scaleX = hold:1
            if(scaleRatioX > SCALE_RATIO_RIGHT_X_THRESHOLD && distanceX < 0 && !isAnimatorStart && Math.abs(scaleRatioX - 1.0f) > 0.1f){
                isAnimatorStart = true;
                animator.setFloatValues(SCALE_RATIO_RIGHT_X_THRESHOLD,1f);
                animator.start();
            }else  if(scaleRatioX < SCALE_RATIO_LEFT_X_THRESHOLD && distanceX > 0 && !isAnimatorStart && Math.abs(scaleRatioX - 0f) > 0.1f){
            //2.(scaleX < hold && 左滑) --> 动画：scaleX = hold : 0
                isAnimatorStart = true;
                animator.setFloatValues(SCALE_RATIO_LEFT_X_THRESHOLD,0f);
                animator.start();
            }else{
                invalidate();
            }
            return false;
        }

        public void onLongPress(MotionEvent e) {

        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

}
