> 最近几个月终于有大把时间总结这两年来所学
2019.5.23

# 前言
- 最近经常交替使用Android和iOS手机。对于两个系统，从我们常用的列表来看，Android一般的列表菜单是通过长按出来的，而iOS是通过滑动出现的。比如我们常用的微信，对于Android版本，长按某个聊天好友，会弹出 `标为未读，置顶聊天，删除聊天`选项；对于iOS的版本，右滑，会显示出`标为未读，删除`选项

# HOW TO USE

```
allprojects {

        repositories {
                ...
                maven { url 'https://jitpack.io' }
        }
        
}
        
dependencies {

        implementation 'com.github.crekerliqiang:SlideView:v0.1.1'
        
}
```   

***
***
*---------------------------------我是分割线---------------------------------*
***
***
# 1. 滑动View

#### 1.1 内容展示
我在Android上面，实现了一个滑动的View，模仿的是微信的iOS版，先简单列举一下功能，直接上图，看着比较直观一些。下面我放了四个动画，分别是：滑动展开，单击，长按，双击。

- 滑动效果
![滑动展开](https://upload-images.jianshu.io/upload_images/7648905-bce2176677546764.gif?imageMogr2/auto-orient/strip)

- 单击选择效果
![单击选择](https://upload-images.jianshu.io/upload_images/7648905-3bfa1ba894480183.gif?imageMogr2/auto-orient/strip)

- 长按、双击效果
![长按和双击效果](https://upload-images.jianshu.io/upload_images/7648905-c60a9fde5e0c5ac3.gif?imageMogr2/auto-orient/strip)
#### 1.2 功能介绍

- 这个滑动View是一个自定义View，里面主要用了属性动画，触摸检测，触摸反馈，配合测量完成。
使用时，只需要在布局文件里面调用就可以，和`TextView`等常用控件一样，像这个样子。

**在activity里面**

```
        slideView = findViewById(R.id.slide_view1);
        slideView.setOnClickListener(new Listener.OnMenuClickListener() {
            @Override
            public void onClick(int id) {
                switch(id){

                    case R.id.menu_a:
                        Util.toast("点击 删除");
                        break;
                    case R.id.menu_b:
                        Util.toast("点击 设为未读");
                        break;
                    case R.id.sure_delete:
                        Util.toast("点击 确认删除");
                        break;
                    case R.id.long_press:
                        Util.toast("长按");
                        VibratorLib.vibrateShort();
                        break;
                    case R.id.double_click:
                        Util.toast("双击");
                        break;
                }
            }
        });
```

**在xml里面**

```
<android.support.constraint.ConstraintLayout
    ......
    <demo.com.library.view.SlideView
        android:id="@+id/slide_view1"
        ...

        app:image_src="@drawable/crekerli_pig"
        app:image_margin_start="10dp"
        app:image_slide_length="60dp"

        app:title_text="@string/title"
        app:title_text_size="20sp"
        app:title_text_color="@color/colorBlack"
        app:title_text_margin_start="10dp"

        app:message_text="@string/message"
        app:message_text_size="12sp"
        app:message_text_color="@color/colorBlack"
        app:message_text_margin_start="10dp"

        app:menu_a_background="@color/colorRed"
        app:menu_a_text="@string/delete"
        app:menu_a_text_size="20sp"
        app:menu_a_aspect="1"

        app:menu_b_background="@color/colorGray"
        app:menu_b_text="@string/set"
        app:menu_b_text_size="20sp"
        app:menu_b_aspect="1.2"/>
        ...
```

从xml文件里面，细心一点儿可以看出我对SlideView的内容分成了 image title message menu_a menu_b 五个部分。对应到View里面，看下面的图示：

![页面展开前](https://upload-images.jianshu.io/upload_images/7648905-6c29ac04a325e155.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![页面展开后](https://upload-images.jianshu.io/upload_images/7648905-ec904dc83c4f2981.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

下面分别介绍一下五个部分。
# 2. 五个部分

#### 2.1 image

image 表示用户头像，里面有三个配置参数

```
app:image_src="@drawable/crekerli_pig"
app:image_margin_start="10dp"
app:image_slide_length="60dp"
```
1. `image_src`：文件资源
2. `image_margin_start`：图片的start位置到view最左侧的偏移量
3. `image_slide_length`：图片的边长「正方形」
#### 2.2 title

```
app:title_text="@string/title"
app:title_text_size="20sp"
app:title_text_color="@color/colorBlack"
app:title_text_margin_start="10dp"
```
1. `title_text`：文本内容
2. `title_text_size`：文本大小
3. `title_text_color`：文本颜色
4. `title_text_margin_start`：文本起始位置的偏移量

#### 2.3 message
```
app:message_text="@string/message"
app:message_text_size="12sp"
app:message_text_color="@color/colorBlack"
app:message_text_margin_start="10dp"
```
1. `message_text` ：文本内容
2. `message_text_size` ：文本大小
3. `message_text_color` ：文本颜色
4. `message_text_margin_start` ：文本起始位置的偏移量

#### 2.4 menu
menu_a 和menu_b的内容是一样的，所以这里放在一起统一讲

```
app:menu_a_background="@color/colorRed"
app:menu_a_text="@string/delete"
app:menu_a_text_size="20sp"
app:menu_a_aspect="1"
```
1. `app:menu_a_backgroundor` ：菜单的背景色
2. `app:menu_a_text` ：菜单的文字内容
3. `app:menu_a_text_size` ：菜单的文字大小
4. `app:menu_a_aspect` ：菜单的宽高比。高度为View的高，不如宽高比2，那么宽就是高的两倍。


***[SlideView GitHub详细地址](https://github.com/crekerliqiang/SlideView)***


再另外，以上都是自己平时所学整理，如果有错误，欢迎留言或者添加微信批评指出，**一起学习，共同进步，爱生活，爱技术**。
![image](http://upload-images.jianshu.io/upload_images/7648905-6e367a351364039f?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
