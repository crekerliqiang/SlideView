package com.cerkerli.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.com.library.Util;
import demo.com.library.view.SlideView;
import demo.com.library.view.SlideViewOnClickListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SlideView view = findViewById(R.id.button);

        view.setMenuOnClickListener(new SlideViewOnClickListener() {
            @Override
            public void onclick(String  menuName) {
                if(getResources().getString(R.string.delete).equals(menuName)){
                    Util.toast("点击. " + menuName);
                }else if(getResources().getString(R.string.set).equals(menuName)){
                    Util.toast("点击: " + menuName);

                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //模拟内存泄露
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 3 * 60 * 1000);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                finish();
//            }
//        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.toast("onDestroy: ");

    }
}
