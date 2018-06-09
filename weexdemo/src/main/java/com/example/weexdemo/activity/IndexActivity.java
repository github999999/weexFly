package com.example.weexdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weexdemo.R;

public class IndexActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_index);

    findViewById(R.id.btn_local).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IndexActivity.this, LocalActivity.class));
      }
    });

    findViewById(R.id.btn_network).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IndexActivity.this, NetworkActivity.class));
      }
    });

    findViewById(R.id.btn_fragment).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IndexActivity.this, WXFragmentActivity.class));
      }
    });

    findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        startActivity(new Intent(IndexActivity.this, JumpActivity.class));
        startActivity(new Intent(IndexActivity.this, Main2Activity.class));
      }
    });
  }
}
