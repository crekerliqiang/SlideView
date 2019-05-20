package com.cerkerli.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;

    private boolean USE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(USE){
//            mRecyclerView = (RecyclerView) findViewById(R .id.rv_main);
//            String[] data = new String[20];
//            for (int i = 0; i < 20; i++) {
//                data[i] = "item" + i;
//            }
//            mMyAdapter = new MyAdapter(this, data);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            mRecyclerView.setAdapter(mMyAdapter);
//
//        }
    }




}
