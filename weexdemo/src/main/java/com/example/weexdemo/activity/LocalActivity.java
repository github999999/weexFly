package com.example.weexdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weexdemo.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

public class LocalActivity extends AppCompatActivity implements IWXRenderListener {

  WXSDKInstance mWXSDKInstance;
  private static final String JS_PATH = "http://192.168.2.121:8888?page=index";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();  //去掉标题栏
    }
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mWXSDKInstance = new WXSDKInstance(this);
    mWXSDKInstance.registerRenderListener(this);
    // 定义参数  可以放到下面方法的参数三处
    Map<String,Object> options=new HashMap<>();
    options.put(WXSDKInstance.BUNDLE_URL,"file://first.js");
    /**
     * 参数一：WXSample 可以替换成自定义的字符串，针对埋点有效。
     * 参数二：template 是.we transform 后的 js文件。
     * 参数三：options 可以为空，或者通过options传入 js需要的参数。例如bundle js的地址等。
     * 参数四：jsonInitData 可以为空。
     * 参数五：
     */
//    mWXSDKInstance.render("WXSample",
//            WXFileUtils.loadAsset("first.js", this),
//            null, null, WXRenderStrategy.APPEND_ASYNC);

//    mWXSDKInstance.render("WXSample",
//            WXFileUtils.loadAsset("first.js", this), options,
//            null, -1, -1, WXRenderStrategy.APPEND_ASYNC);


    mWXSDKInstance.renderByUrl("WXSample", JS_PATH, null, null, WXRenderStrategy
            .APPEND_ASYNC/*, WeexConstants.JS_MAX_STALE_YEAR*/);
  }

  @Override
  public void onViewCreated(WXSDKInstance instance, View view) {
    // 处理view的加载 大小
    setContentView(view);
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
  public void onException(WXSDKInstance instance, String errCode, String msg) {
    // 加载weex页面失败 使用webview降级处理
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
