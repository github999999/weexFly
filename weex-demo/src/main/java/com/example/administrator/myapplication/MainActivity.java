package com.example.administrator.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.taobao.weex.WXSDKInstance;

public class MainActivity extends WXBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String JS_PATH = "assets/dist/home/home-live.js";
    private FrameLayout mContainer;
    private ProgressBar mProgressBar;
    private View mWAView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();  //去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (FrameLayout) findViewById(R.id.container);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        setViewGlobalLayoutListener(mContainer);
        Log.e("MainActivity","----------onCreate------------");
    }

    @Override
    protected String getPageName() {
        return TAG;
    }
    @Override
    protected String getAssembleFilePath() {
        return JS_PATH;
    }
    @Override
    protected ViewGroup getContainerLayout() {
        return mContainer;
    }
    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (mWAView != null && mContainer != null && mWAView.getParent() == mContainer) {
            mContainer.removeView(mWAView);
        }

        if (mContainer != null) {
            mWAView = view;
            mContainer.removeAllViews();
            mContainer.addView(view);
            mContainer.requestLayout();
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode,
                            String msg) {
        super.onException(instance, errCode, msg);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void refreshWithJsFile(String filePath) {}
}