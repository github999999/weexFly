package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.myapplication.util.WeexConstants;
import com.example.administrator.myapplication.util.WeexFileUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXRenderErrorCode;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.IWXDebugProxy;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.component.NestedContainer;
import java.util.HashMap;
import java.util.Map;


/**
 * weex页面基类
 */
public abstract class WXBaseActivity extends AppCompatActivity implements IWXRenderListener,
        WXSDKInstance.NestedInstanceInterceptor{

    private static final String TAG = WXBaseActivity.class.getSimpleName();

    protected WXSDKInstance wxsdkInstance;
    private BroadcastReceiver broadcastReceiver;

    private double mContentWidth;   // 加载内容的宽度
    private double mContentHeight;  // 加载内容的高度

    /**
     * 配置参数
     */
    protected HashMap<String, Object> configMap = new HashMap<String, Object>();

    @Override
    protected void onResume() {
        super.onResume();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {

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
        // weex加载异常，webview降级处理
        if (TextUtils.equals(WXRenderErrorCode.WX_NETWORK_ERROR, errCode)) {
            ViewGroup containerLayout = getContainerLayout();
            if (instance != null && containerLayout != null) {
                containerLayout.removeAllViews();
                containerLayout.addView(getReloadView());
            }
        }
    }

    @Override
    public void onCreateNestInstance(WXSDKInstance instance, NestedContainer container) {

    }

    /**
     * 获取view宽高的监听
     * @param view
     */
    protected void setViewGlobalLayoutListener(final View view) {
        if (view != null) {
            // 获取view宽高的监听
            view.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            } else {
                                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }
                            loadWXfromLocalOrUrl(view);
                        }
                    });
        }
    }

    private void loadWXfromLocalOrUrl(View view) {
        if (wxsdkInstance == null) {
            wxsdkInstance = new WXSDKInstance(this);
            wxsdkInstance.registerRenderListener(this);
            wxsdkInstance.setNestedInstanceInterceptor(this);
        }
        mContentWidth = view.getWidth();
        mContentHeight = view.getHeight();

        // 就是在子线程中调用这个方法可以更新UI
        view.post(new Runnable() {
            @Override
            public void run() {
                // 这里其实是在主线程中执行的
                Rect outRect = new Rect();
                // 获取窗口可视区域大小
                getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
                reloadContent(mContentHeight);
            }
        });
    }

    /**
     * 加载本地的js文件
     * @param path
     */
    private void loadFromAssets(String path) {
        wxsdkInstance.render(getPageName(), WeexFileUtil.loadWeexJs(path, WXBaseActivity.this),
                configMap, null, WXRenderStrategy.APPEND_ASYNC);
    }

    /**
     * 加载网络文件
     * @param path
     */
    protected void loadFormUrl(String path) {
        wxsdkInstance.renderByUrl(getPageName(), path, configMap, null, WXRenderStrategy
                .APPEND_ASYNC/*, WeexConstants.JS_MAX_STALE_YEAR*/);
    }

    /**
     * 获取包名
     * @return
     */
    protected abstract String getPageName();

    /**
     * 获取本地文件路径
     * @return
     */
    protected abstract String getAssembleFilePath();

    /**
     * 刷新js文件
     * @param filePath
     */
    protected abstract void refreshWithJsFile(String filePath);

    /**
     * 获取布局
     * @return
     */
    protected abstract ViewGroup getContainerLayout();

    public class RefreshBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (IWXDebugProxy.ACTION_DEBUG_INSTANCE_REFRESH.equals(intent.getAction())) {
//                LogUtil.v(TAG, "connect to debug server success");
                Bundle bundle = intent.getExtras();
                String jsFilePath = bundle.getString("jsFilePath");
                if (!TextUtils.isEmpty(jsFilePath)) {
                    refreshWithJsFile(jsFilePath);
                }
            }
        }
    }

    /**
     * 注册广播
     */
    protected void registerBroadcastReceiver() {
        broadcastReceiver = new RefreshBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IWXDebugProxy.ACTION_DEBUG_INSTANCE_REFRESH);
        registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 反注册广播
     */
    protected void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    /**
     * 获取固定的参数
     * @return
     */
    protected Map<String, Object> getFixedParams() {
        String uid = "";
        Map<String, Object> params = new HashMap<>();
        params.put(WeexConstants.IS_TEST, true);    // 测试
        params.put(WeexConstants.PLAT, "android");  // 终端
        params.put(WeexConstants.UID, uid);
        params.put(WeexConstants.PRODUCT, "app");
        params.put(WeexConstants.VERSION, "1.0.0");
        params.put(WeexConstants.OS_VERSION, Build.VERSION.RELEASE);
        if (getPageParams() != null && getPageParams().size() != 0) {
            params.putAll(getPageParams());
        }
        return params;
    }

    /**
     * 页面自定义键值
     *
     * @return
     */
    protected Map<String, Object> getPageParams() {
        return null;
    }


    /**
     * 重新加载图片控件
     * @return
     */
    private View getReloadView() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(com.taobao.weappplus_sdk.R.drawable.error);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) mContentWidth
                / 2, (int) mContentWidth / 2);
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setOnClickListener(null);
                v.setEnabled(false);
                reloadContent(mContentHeight);
            }
        });
        return imageView;
    }

    /**
     * 重新加载内容
     * @param height
     */
    private void reloadContent(double height) {
        String path = getAssembleFilePath();
        configMap.put(WeexConstants.BUNDLE_URL, path);
        configMap.put(WeexConstants.CONTENT_HEIGHT, height);
        configMap.put(WeexConstants.ENDPOINT_CDN, WeexConstants.ENDPOINT_SNS_CDN);
        configMap.put(WeexConstants.PARAMS, getFixedParams());
        if (WeexFileUtil.isAssets(path)) {
            loadFromAssets(path);
//            LogUtil.d(TAG, "weex js laod path from assets:" + path);
        } else if (WeexFileUtil.isHttp(path)) {
            loadFormUrl(path);
//            LogUtil.d(TAG, "weex js laod path from url:" + path);
        } else {
//            LogUtil.d(TAG, "weex js laod path not support");
        }
    }

//    protected void reloadContent() {
//        if (mContentHeight > 0) {
//            reloadContent(mContentHeight);
//        }
//    }
}
