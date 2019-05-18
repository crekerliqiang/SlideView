package com.cerkerli.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.com.library.Util;
import demo.com.library.view.SlideView;
import demo.com.library.view.SlideViewOnClickListener;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getSimpleName()+"111";

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
}
