package com.cerkerli.slideview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import demo.com.library.view.SlideView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideView view = findViewById(R.id.button);

        List<String> menuString = new ArrayList<>();
        menuString.add("删除");
        menuString.add("未读");
        view.putMenuString(menuString);
        List<Integer> menuColor = new ArrayList<>();
        menuColor.add(Color.RED);
        menuColor.add(Color.GRAY);
        view.putMenuColor(menuColor);
    }
}
