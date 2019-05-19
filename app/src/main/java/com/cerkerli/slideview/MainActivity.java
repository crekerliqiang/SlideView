package com.cerkerli.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.com.library.Util;
import demo.com.library.view.SlideView;
import demo.com.library.view.SlideViewOnClickListener;

public class MainActivity extends AppCompatActivity {

    boolean change = true;
    SlideView view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.button);

        view.setMenuOnClickListener(new SlideViewOnClickListener() {
            @Override
            public void onClick(int id) {
                switch (id){
                    case R.id.menu_a:
                        Util.toast("点击 A");
                        break;
                    case R.id.menu_b:
                        Util.toast("点击 B");
                        break;
                    case R.id.sure_delete:
                        Util.toast("点击 SURE");
                        break;
                }
//                changeView();
            }


        });


    }

    private void changeView(){
        if(change){
            change = false;
            view.setMenuTextString( "设为未读","删除");
        }else{
            change = true;
            view.setMenuTextString("删除", "设为未读");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.toast("onDestroy: ");

    }
}
