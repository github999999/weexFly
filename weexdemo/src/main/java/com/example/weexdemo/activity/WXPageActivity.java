package com.example.weexdemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.weexdemo.R;
import com.example.weexdemo.utils.MapUtil;
import com.orhanobut.logger.Logger;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;
import java.util.HashMap;
import java.util.Map;


/**
 * weex通过navigator跳转的activity页面
 */
public class WXPageActivity extends AppCompatActivity implements IWXRenderListener {

    WXSDKInstance mWXSDKInstance;
    Uri mUri;
    private String paramF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();  //去掉标题栏
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        Uri uri = getIntent().getData();
        Bundle bundle = getIntent().getExtras();
        if (uri != null) {
            mUri = uri;
        }

        if (bundle != null) {
            String bundleUrl = bundle.getString("bundleUrl");
            if (!TextUtils.isEmpty(bundleUrl)) {
                mUri = Uri.parse(bundleUrl);
            }
        }

        if (mUri == null) {
            Toast.makeText(this, "the uri is empty!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String path = mUri.toString();
        // 传来的url参数总会带上http:/ 应该是个bug 可以自己判断是否本地url再去跳转
//        String jsPath = path.indexOf("weex/js/") > 0 ? path.replace("http:/", "") : path;

        Logger.e(path);

        if (path.contains("fly")) {   // 跳转的是qitayemian
            int index = path.lastIndexOf("?");
            Logger.e(index+"");
//            String substring = path.substring(0,index).replace("?fly","");
            String substring = path.substring(0,index);
            Logger.e(substring);

            paramF = path.substring(index+1);
            Logger.e(paramF);

            HashMap<String, Object> options = new HashMap<String, Object>();
            options.put(WXSDKInstance.BUNDLE_URL, path);
            mWXSDKInstance = new WXSDKInstance(this);
            mWXSDKInstance.registerRenderListener(this);
//        mWXSDKInstance.render("WXSample", WXFileUtils.loadAsset(jsPath, this), options, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
            mWXSDKInstance.renderByUrl("WXSample", substring, null, null, WXRenderStrategy
                    .APPEND_ASYNC/*, WeexConstants.JS_MAX_STALE_YEAR*/);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String,Object> params = new HashMap<>();
                params.put("key","value");
                mWXSDKInstance.fireGlobalEventCallback("kingfly", MapUtil.convertToMap(paramF));
                Log.e("NetworkActivity =","执行发送事件");
            }
        },1000);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        // 处理view的加载 大小
        setContentView(view);
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        // 加载weex页面失败 使用webview降级处理
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        // 渲染成功
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        // 刷新成功
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
