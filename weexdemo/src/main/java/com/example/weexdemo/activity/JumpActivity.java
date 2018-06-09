package com.example.weexdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.weexdemo.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;

/**
 * 利用weex跳转
 */
public class JumpActivity extends AppCompatActivity implements IWXRenderListener {

    WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        // 定义参数  可以放到下面方法的参数三处
        HashMap<String, Object> options = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("platform", "Android");
        options.put("params", params);
        String jsPath = "js/index.js";
        options.put(WXSDKInstance.BUNDLE_URL, jsPath);

        /**
         * 参数一：WXSample 可以替换成自定义的字符串，针对埋点有效。
         * 参数二：template 是.we transform 后的 js文件。
         * 参数三：option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
         * 参数四：jsonInitData 可以为空。
         * 参数五：
         */
        mWXSDKInstance.render(
                "WXSample",
                WXFileUtils.loadAsset(jsPath, this),
                options,
                null,
                -1,
                -1,
                WXRenderStrategy.APPEND_ASYNC);
    }

    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
        // 处理view的加载 大小
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {
        // 渲染成功
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {
        // 刷新成功
    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {
        // 加载weex页面失败 使用webview降级处理
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
