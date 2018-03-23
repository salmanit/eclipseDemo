package com.charlie.demo0108;

import java.io.FileInputStream;

import com.charlie.demo0108.R;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import heart.HeartView;

public class ActivityHeartView extends ActivityBase {
    HeartView heartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_view);
        heartView = (HeartView) findViewById(R.id.surfaceView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        heartView.reDraw();
        return super.onTouchEvent(event);
    }

    public void reDraw(View v) {

        heartView.reDraw();
    }

}
