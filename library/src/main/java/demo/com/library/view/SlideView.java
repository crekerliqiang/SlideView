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
import java.util.HashMap;
import java.util.List;

import demo.com.library.LLog;
import demo.com.library.R;
import demo.com.library.Util;
import demo.com.library.ViewConfigException;

import static demo.com.library.Constants.IMAGE_MARGIN_START_DEFAULT;
import static demo.com.library.Constants.IMAGE_SLIDE_LEN_DEFAULT;
import static demo.com.library.Constants.KEY_X_END;
import static demo.com.library.Constants.KEY_X_START;
import static demo.com.library.Constants.MENU_A_BACKGROUND_DEFAULT;
import static demo.com.library.Constants.MENU_BACKGROUND_ASPECT_DEFAULT;
import static demo.com.library.Constants.MENU_B_BACKGROUND_DEFAULT;
import static demo.com.library.Constants.MENU_TEXT_SIZE_DEFAULT;
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

    /**
     * Paint : 绘制Menu的背景
     */
    private Paint paintMenuBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * Paint:绘制Menu的文本
     */
    private Paint paintMenuText = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * Paint : 绘制Title文本
     */
    private Paint paintTitleText = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * Paint : 绘制Image的Bitmap
     */
    private Paint paintImageBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * Paint : 绘制Message的文本
     */
    private Paint paintMessageText = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 触屏监听
     */
    private MyGestureListener gestureListener;
    /**
     * 触屏检测
     */
    private GestureDetectorCompat detectorCompat;
    /**
     * 设置点击菜单栏监听 -- 提供给开发者使用
     */
    SlideViewOnClickListener onClickListener;
    public void setMenuOnClickListener(SlideViewOnClickListener listener){
        onClickListener = listener;
    }
    /**
     * 滑动效果的动画
     */
    ObjectAnimator wholeViewAnimator = ObjectAnimator.ofFloat(this,"scaleWholeViewRatioX",1f);

    /**
     * 动画是否开始
     */
    boolean isWholeViewAnimatorStart = false;

    /**
     * Image Title Message Menu 的 水平方向的缩放比例
     * 同时也是自定义属性动画的参数
     */
    float scaleWholeViewRatioX = 1.0f;
    public float getScaleWholeViewRatioX() {
        return scaleWholeViewRatioX;
    }
    public void setScaleWholeViewRatioX(float scaleWholeViewRatioX) {
        this.scaleWholeViewRatioX = scaleWholeViewRatioX;
        invalidate();
    }
    //Image 相关
    /**
     * Image : source 用于获取Bitmap
     */
    int imageSource;

    /**
     * Image: bitmap,get by @ImageSource
     */
    Bitmap bitmap;

    /**
     * Image : image的边长
     */
    int imageSlideLength;

    /**
     * Image : image的起始偏移量
     */
    float imageMarginStart;

    /**
     * 是否绘制 Image
     * 开发者没有设置 image 时，就不绘制bitmap
     */
    boolean isDrawBitmap;

    //Title 相关
    /**
     * Title:文本内容
     */
    String titleText;

    /**
     * Title:文本大小
     */
    int titleTextSize;

    /**
     * Title:文本颜色
     */
    int titleTextColor;

    /**
     * Title:文本的起始偏移
     */
    int titleTextMarginStart;

    /**
     * Title: 文本的高度
     */
    int titleViewHeight;

    /**
     * Title:文字绘制时的起始X位置
     */
    int titleTextOffsetX;

    /**
     * Title:文字绘制时的起始Y位置
     */
    int titleTextOffsetY;

    //Message
    /**
     * Message : 文本内容
     */
    String messageText;

    /**
     * Message : 文本大小
     */
    int messageTextSize;

    /**
     * Message : 文本颜色
     */
    int messageTextColor;

    /**
     * Message : 文本的起始偏移
     */
    int messageTextMarginStart;

    /**
     * Message : 文本的高度
     */
    int messageViewHeight;

    /**
     * Message : 文字绘制时的起始X位置
     */
    int messageTextOffsetX;

    /**
     * Message : 文字绘制时的起始X位置
     */
    int messageTextOffsetY;


    //滑动菜单区域
    /**
     * Menu : 菜单的文字 X 轴绘制位置
     */
    int menuTextOffsetX;

    /**
     * Menu : 菜单的文字 Y 轴绘制位置
     */
    int menuTextOffsetY;

    /**
     * Menu : 所有菜单的背景 X 轴绘制的起始位置
     */
    List<Integer> menuBackgroundOffsetX = new ArrayList<>();

    /**
     * 存储每一个菜单背景的起始值的X轴的大小
     */
    List<HashMap<String,Integer>> menuBackgroundStartEndX = new ArrayList<>();

    /**
     * Menu : 每个菜单背景的宽度
     */
    List<Integer> menuBackgroundWidthList = new ArrayList<>();

    /**
     * Menu : 菜单的背景 X 方向已经使用了的宽度
     */
    int menuBackgroundWidthUsed = 0;

    /**
     * Menu : 所有 菜单背景的平均宽度
     */
    int menuBackgroundWidthAverage;
    /**
     * Menu : 菜单背景的高度
     */
    int menuBackgroundHeight;

    /**
     * Menu : 菜单是否是展开状态，用于判断点击事件
     */
    boolean isMenuExpand = false;

    /**
     * Menu : 所有菜单的文本内容
     */
    List<String> menuTextString = new ArrayList<>();

    /**
     * Menu : 所有菜单的背景颜色
     */
    List<Integer> menuBackgroundColor = new ArrayList<>();

    /**
     * Menu : 所有菜单背景的宽高比
     */
    List<Float> menuBackgroundAspect = new ArrayList<>();

    /**
     * Menu : 所有菜单的文字大小
     */
    List<Integer> menuTextSize = new ArrayList<>();

    /**
     * Menu SURE: 确认删除标志位
     */
    boolean isMenuDeleted = false;
    /**
     * Menu SURE: 确定字符串
     */
    final String SURE;
    /**
     * MENU SURE: 显示确定删除的动画
     */
    ObjectAnimator sureBackgroundAnimator =  ObjectAnimator.ofFloat(this,"scaleSureBackgroundViewRatioX",1f);

    /**
     * Menu SURE : 动画的缩放参数
     */
    float scaleSureBackgroundViewRatioX = 1f;

    /**
     * scaleSureBackgroundViewRatioX get 方法
     * @return scaleSureBackgroundViewRatioX
     */
    public float getScaleSureBackgroundViewRatioX() {
        return scaleSureBackgroundViewRatioX;
    }

    /**
     *  scaleSureBackgroundViewRatioX set 方法
     * @param scaleSureBackgroundViewRatioX 缩放参数
     */
    public void setScaleSureBackgroundViewRatioX(float scaleSureBackgroundViewRatioX) {
        this.scaleSureBackgroundViewRatioX = scaleSureBackgroundViewRatioX;
        invalidate();
    }
    /**
     * Menu SURE : 显示文本的动画
     */
    ObjectAnimator sureTextAnimator =  ObjectAnimator.ofFloat(this,"scaleSureTextRatioX",0f);

    /**
     * Menu SURE : 显示文本的
     */
    float scaleSureTextRatioX = 0f;
    public float getSscaleSureTextRatioX() {
        return scaleSureTextRatioX;
    }

    public void setScaleSureTextRatioX(float scaleSureTextRatioX) {
        this.scaleSureTextRatioX = scaleSureTextRatioX;
        invalidate();
    }


    /**
     * View 的初始化
     * @param context 上下文
     * @param attrs 属性集
     */
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
        messageTextMarginStart = typedArray.getDimensionPixelSize(R.styleable.SlideView_message_text_margin_start,MESSAGE_MARGIN_START_DEFAULT);
        //4 Menu a
        String menuString = typedArray.getString(R.styleable.SlideView_menu_a_text);
        int menuColor = typedArray.getColor(R.styleable.SlideView_menu_a_background,MENU_A_BACKGROUND_DEFAULT);
        float menuAspect = typedArray.getFloat(R.styleable.SlideView_menu_a_aspect, MENU_BACKGROUND_ASPECT_DEFAULT);
        int menuTextSize = typedArray.getDimensionPixelSize(R.styleable.SlideView_menu_a_text_size,MENU_TEXT_SIZE_DEFAULT);
        if(menuString != null){
            this.menuTextString.add(menuString);
            this.menuBackgroundColor.add(menuColor);
            this.menuBackgroundAspect.add(menuAspect);
            this.menuTextSize.add(menuTextSize);
        }



        // 5 Menu b
        menuString = typedArray.getString(R.styleable.SlideView_menu_b_text);
        menuColor = typedArray.getColor(R.styleable.SlideView_menu_b_background,MENU_B_BACKGROUND_DEFAULT);
        menuAspect = typedArray.getFloat(R.styleable.SlideView_menu_b_aspect, MENU_BACKGROUND_ASPECT_DEFAULT);
        menuTextSize = typedArray.getDimensionPixelSize(R.styleable.SlideView_menu_b_text_size,MENU_TEXT_SIZE_DEFAULT);
        if(menuString != null){
            this.menuTextString.add(menuString);
            this.menuBackgroundColor.add(menuColor);
            this.menuBackgroundAspect.add(menuAspect);
            this.menuTextSize.add(menuTextSize);
        }
        typedArray.recycle();
        //获取固定字符串"sure"
        SURE = context.getString(R.string.sure);
        //设置动画时间
        wholeViewAnimator.setDuration(500);
        //Title文字规则
        paintTitleText.setColor(messageTextColor);
        paintTitleText.setTextSize(titleTextSize);
        //Message文字规则
        paintMessageText.setColor(titleTextColor);
        paintMessageText.setTextSize(messageTextSize);
        //初始化点击监听
        gestureListener = new MyGestureListener();
            //设置滑动点击
        detectorCompat = new GestureDetectorCompat(context,gestureListener);
            //设置双手点击[实际使用只用单手]
        detectorCompat.setOnDoubleTapListener(gestureListener);
        //动画结束监听
        wholeViewAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                isWholeViewAnimatorStart = false;
                //scaleWholeViewRatioX 为 0 时，表示展开
                isMenuExpand = Util.isFloatEqual(scaleWholeViewRatioX, 0f);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 界面改变时调用，用于初始化各种参数
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        titleViewHeight = (int)((float)getHeight() * TITLE_HEIGHT);
        messageViewHeight = getHeight() - titleViewHeight;
        //title x y 的坐标
        titleTextOffsetX = TEXT_OFFSET_X + bitmap.getWidth() + titleTextMarginStart + (int)imageMarginStart;
        titleTextOffsetY = (titleViewHeight + titleTextSize)/2 - TEXT_OFFSET_Y;
        //对Title的文本长度做限制
        int mostTextSize = (int)((getWidth() - titleTextOffsetX)*TEXT_MAX_RATIO);
        if(titleTextSize * titleText.length() > mostTextSize){
            titleText = Util.cutText(titleText,titleTextSize,mostTextSize);
        }
        //message x y 的坐标
        messageTextOffsetX = TEXT_OFFSET_X + bitmap.getWidth() + messageTextMarginStart + (int)imageMarginStart;
        messageTextOffsetY = getHeight() - (messageViewHeight - messageTextSize)/2 - TEXT_OFFSET_Y*2;
        //对Message的文本长度做限制
        mostTextSize = (int)((getWidth() - messageTextOffsetX)*TEXT_MAX_RATIO);
        if(messageTextSize * messageText.length() > mostTextSize){
            messageText = Util.cutText(messageText,messageTextSize,mostTextSize);
        }
        //菜单的背景高度 [高度默认]
        menuBackgroundHeight = getHeight();
        //菜单背景的高度
        for(int i = 0; i < menuTextString.size(); i++){
            //菜单背景的宽度
            int width = (int)((float)menuBackgroundHeight * menuBackgroundAspect.get(i));
            menuBackgroundWidthList.add(width);
            //记录背景已经使用了的长度
            menuBackgroundWidthUsed += width;
            //根据已经使用了的长度计算偏移
            int offsetX = getWidth() - menuBackgroundWidthUsed;
            menuBackgroundOffsetX.add(offsetX);
            //存储一下背景的数据，点击的时候用到
            HashMap<String,Integer> map = new HashMap<>();
            map.put(KEY_X_START, offsetX);
            map.put(KEY_X_END, offsetX + width);
            menuBackgroundStartEndX.add(map);

        }
        menuBackgroundWidthUsed = 0;
        //获取宽度的平均值
        menuBackgroundWidthAverage = Util.getListAverage(menuBackgroundWidthList);
        //设置Sure动画的数据范围
        sureBackgroundAnimator.setFloatValues(1f,(float)menuBackgroundWidthAverage * (float)menuTextString.size()/ (float)menuBackgroundWidthList.get(0));
        //设置Sure文字动画的参数范围
//        计算公式：
//        MAX = (X总背景 - X背景1)/2 + (X当前文字长度 - X原来文字长度)/2    注：(X当前文字长度 - X原来文字长度) 相当于 SURE 变量
        sureTextAnimator.setFloatValues(0,
                (menuBackgroundWidthAverage * menuBackgroundWidthList.size() - (float)menuBackgroundWidthList.get(0))/2f +
                (float)SURE.length() * (float)menuTextSize.get(0)/2f);
    }

    /**
     * 绘制方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //移动
        canvas.translate(menuTextString.size() * menuBackgroundWidthAverage * (scaleWholeViewRatioX - 1f),0);
        //绘制 image
        if(isDrawBitmap)canvas.drawBitmap(bitmap,imageMarginStart, (getHeight() - bitmap.getHeight())>>1, paintImageBitmap);
        //绘制 title
        canvas.drawText(titleText,titleTextOffsetX ,titleTextOffsetY , paintTitleText);
        //绘制message
        canvas.drawText(messageText,messageTextOffsetX,messageTextOffsetY, paintMessageText);
        canvas.restore();
        //绘制菜单区域
        //绘制 Menu
        //缩放[]
        canvas.scale(1 - scaleWholeViewRatioX, 1,getWidth(),getHeight()>>1);
        //绘制背景和文字
        for(int i = 0; i < menuTextString.size(); i++){
            String menuText = menuTextString.get(i);
            //需要绘制删除确认菜单
            if(isMenuDeleted){
                //只绘制一个
                if(i == 1)break;
                menuText = SURE + menuText;
                canvas.save();
                canvas.scale(scaleSureBackgroundViewRatioX, 1,getWidth(),getHeight()>>1);
            }
            //绘制背景
            paintMenuBackground.setColor(menuBackgroundColor.get(i));
            canvas.drawRect(menuBackgroundOffsetX.get(i),0,menuBackgroundOffsetX.get(i) + menuBackgroundWidthList.get(i),
                    menuBackgroundHeight, paintMenuBackground);
            paintMenuBackground.reset();

            //
            if(isMenuDeleted)canvas.restore();

            if(isMenuDeleted)canvas.translate(-scaleSureTextRatioX,0);
            //绘制文字
            int textSize = menuTextSize.get(i);
            paintMenuText.setTextSize(textSize);
            menuTextOffsetX = menuBackgroundOffsetX.get(i) +
                    (menuBackgroundWidthList.get(i) - menuTextString.get(i).length() * textSize)/2;
            menuTextOffsetY = (menuBackgroundHeight + textSize)/2;

            canvas.drawText(menuText, menuTextOffsetX + TEXT_OFFSET_X,
                    menuTextOffsetY - TEXT_OFFSET_Y, paintMenuText);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detectorCompat.onTouchEvent(event);
    }

    public class MyGestureListener implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{

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


            scaleWholeViewRatioX = scaleWholeViewRatioX - distanceX/getWidth();
            if(scaleWholeViewRatioX < 0f)scaleWholeViewRatioX = 0f;
            if(scaleWholeViewRatioX > 1f)scaleWholeViewRatioX = 1f;

//            LLog.d(TAG,"dx " + distanceX + " scale " + s + ":" + scaleWholeViewRatioX );

            //1.(scaleX > hold && 右滑) --> 动画：scaleX = hold:1
            if(scaleWholeViewRatioX > SCALE_RATIO_RIGHT_X_THRESHOLD && distanceX < 0 && !isWholeViewAnimatorStart && Math.abs(scaleWholeViewRatioX - 1.0f) > 0.1f){
                isWholeViewAnimatorStart = true;
                wholeViewAnimator.setFloatValues(SCALE_RATIO_RIGHT_X_THRESHOLD,1f);
                wholeViewAnimator.start();
                //确认删除的标志位取消
                isMenuDeleted = false;
            }else  if(scaleWholeViewRatioX < SCALE_RATIO_LEFT_X_THRESHOLD && distanceX > 0 && !isWholeViewAnimatorStart && Math.abs(scaleWholeViewRatioX - 0f) > 0.1f){
            //2.(scaleX < hold && 左滑) --> 动画：scaleX = hold : 0
                isWholeViewAnimatorStart = true;
                wholeViewAnimator.setFloatValues(SCALE_RATIO_LEFT_X_THRESHOLD,0f);
                wholeViewAnimator.start();
                //确认删除的标志位取消
                isMenuDeleted = false;
            }else{
                invalidate();
            }
            return false;
        }

        public void onLongPress(MotionEvent e) {

            if(!isMenuExpand)Util.toast("long press");

        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        //确定是单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //菜单栏展开、开发者设置了监听
            if(!isMenuExpand || onClickListener == null)return false;

            HashMap<String,Integer> hashMap;

            //确定点击后继续点击[确定删除]
            if(isMenuDeleted){

                hashMap = menuBackgroundStartEndX.get(menuBackgroundStartEndX.size() - 1);
                Integer startX = hashMap.get(KEY_X_START);

                hashMap = menuBackgroundStartEndX.get(0);
                Integer endX = hashMap.get(KEY_X_END);

                if(startX == null || endX == null)throw new ViewConfigException("menuBackgroundStartEndX exception,check its resource");

                if(e.getX() > startX && e.getX() < endX){
                    onClickListener.onClick(R.id.sure_delete);
                }
                return false;
            }
            //不是确定点击的情况
            for(int i = 0; i < menuBackgroundStartEndX.size(); i++){
                hashMap = menuBackgroundStartEndX.get(i);
                Integer X1 = hashMap.get(KEY_X_START);
                Integer X2 = hashMap.get(KEY_X_END);

                if(X1 == null || X2 == null)throw new ViewConfigException("menuBackgroundStartEndX exception,check its resource");

                if( e.getX() > X1 && e.getX() < X2){
                    switch (i){
                        case 0:
                            onClickListener.onClick(R.id.menu_a);
                            //显示是否删除的提示框
                            isMenuDeleted = true;
                            sureBackgroundAnimator.setDuration(400);
                            sureBackgroundAnimator.start();

                            sureTextAnimator.setDuration(400);
                            sureTextAnimator.start();
                            break;
                        case 1:
                            onClickListener.onClick(R.id.menu_b);
                            break;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }



}
