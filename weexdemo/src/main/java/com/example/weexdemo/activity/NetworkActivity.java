package com.example.weexdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.example.weexdemo.R;
import com.example.weexdemo.base.WXBaseActivity;
import com.example.weexdemo.bean.DataBean;
import com.example.weexdemo.utils.AndroidWorkaround;
import com.example.weexdemo.utils.DensityUtil;
import com.taobao.weex.WXSDKInstance;

import java.util.HashMap;
import java.util.Map;

public class NetworkActivity extends WXBaseActivity {
    private FrameLayout mContainer;
    private static final String TAG = NetworkActivity.class.getSimpleName();
    private static final String JS_PATH = "http://192.168.2.103:9999?page=index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        mContainer = findViewById(R.id.container);
        setViewGlobalLayoutListener(mContainer);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        int screenHeight = DensityUtil.getScreenHeight(this);
        Log.e("NetworkActivity-flyfly","screenHeight = " + screenHeight);
        DataBean dataBean = new DataBean();
        dataBean.height = screenHeight+ "";
        Map<String,Object> params = new HashMap<>();
        params.put("key",dataBean);
        wxsdkInstance.fireGlobalEventCallback("geolocation", params);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (view != null && mContainer != null && view.getParent() == mContainer) {
            mContainer.removeView(view);
        }

        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer.addView(view);
            mContainer.requestLayout();
        }
    }

    @Override
    protected boolean isEventBus() {
        return false;
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
    protected void refreshWithJsFile(String filePath) {

    }

    @Override
    protected ViewGroup getContainerLayout() {
        return mContainer;
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode,
                            String msg) {
        super.onException(instance, errCode, msg);
    }
}
