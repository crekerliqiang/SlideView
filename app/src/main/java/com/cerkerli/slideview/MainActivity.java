package com.cerkerli.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.com.library.utils.Util;
import demo.com.library.utils.VibratorLib;
import demo.com.library.view.Listener;
import demo.com.library.view.SlideView;

public class MainActivity extends AppCompatActivity {

    SlideView slideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    }







}
