package com.example.weexdemo.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weexdemo.R;
import com.example.weexdemo.extend.compontent.Progress;

public class Main2Activity extends AppCompatActivity {
    private int preColor = Color.parseColor("#2c2200");
    private int progressColor = Color.parseColor("#6bb849");
    private int CircleColor = Color.parseColor("#ffffff");  // 圆环内部背景颜色
    private int textColor = Color.parseColor("#9bb879");
    private Progress pv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pv = (Progress) findViewById(R.id.progressview1);
        pv.setTextColor(textColor).setCircleBackgroud(CircleColor)
                .setPreProgress(progressColor).setProgress(preColor)
                .setProdressWidth(50).setPaddingscale(0.8f);
        han.sendEmptyMessageDelayed(1, 100);
    }

    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            pv.setValue(msg.what);
            han.sendEmptyMessageDelayed(msg.what + 1, 100);
        };
    };
}
