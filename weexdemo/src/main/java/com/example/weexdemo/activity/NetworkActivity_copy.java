//package com.example.weexdemo.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.example.weexdemo.R;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.taobao.weex.IWXRenderListener;
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.common.WXRenderStrategy;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class NetworkActivity_copy extends AppCompatActivity implements IWXRenderListener {
//
////  private static String TEST_URL = "http://dotwe.org/raw/dist/6fe11640e8d25f2f98176e9643c08687.bundle.js";
//  private static String TEST_URL = "http://192.168.2.102:9999?page=index";
////  private static String TEST_URL = "http://192.168.2.121:8100/dist/EPTask.js";
//  private WXSDKInstance mWXSDKInstance;
//  private FrameLayout mContainer;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_network);
//
//    RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
//    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//      @Override
//      public void onRefresh(RefreshLayout refreshlayout) {
//        refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//      }
//    });
//
//    refreshLayout.setEnableLoadMore(false);
////    refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
////      @Override
////      public void onLoadMore(RefreshLayout refreshlayout) {
////        refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
////      }
////    });
//
//    mContainer = findViewById(R.id.container);
//
//    mWXSDKInstance = new WXSDKInstance(this);
//    mWXSDKInstance.registerRenderListener(this);
//    /**
//     * pageName:自定义，一个标示符号。
//     * url:远程bundle JS的下载地址
//     * options:初始化时传入WEEX的参数，比如 bundle JS地址
//     * flag:渲染策略。WXRenderStrategy.APPEND_ASYNC:异步策略先返回外层View，其他View渲染完成后调用onRenderSuccess。WXRenderStrategy.APPEND_ONCE 所有控件渲染完后后一次性返回。
//     */
//    Map<String, Object> options = new HashMap<>();
//    options.put(WXSDKInstance.BUNDLE_URL, TEST_URL);
//    mWXSDKInstance.renderByUrl("WXSample",
//            TEST_URL,options,null,WXRenderStrategy.APPEND_ONCE);
//  }
//
//  @Override
//  protected void onStart() {
//    super.onStart();
//    if (mWXSDKInstance != null) {
//      mWXSDKInstance.onActivityStart();
//    }
//  }
//
//  @Override
//  protected void onResume() {
//    super.onResume();
//    if (mWXSDKInstance != null) {
//      mWXSDKInstance.onActivityResume();
//    }
//  }
//
//  @Override
//  protected void onPostResume() {
//    super.onPostResume();
//
//    new Handler().postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        Map<String,Object> params = new HashMap<>();
//        params.put("key","value");
//        mWXSDKInstance.fireGlobalEventCallback("geolocation", params);
//        Log.e("NetworkActivity =","执行发送事件");
//      }
//    },2000);
//
//  }
//
//  @Override
//  protected void onPause() {
//    super.onPause();
//    if (mWXSDKInstance != null) {
//      mWXSDKInstance.onActivityPause();
//    }
//  }
//
//  @Override
//  protected void onStop() {
//    super.onStop();
//    if (mWXSDKInstance != null) {
//      mWXSDKInstance.onActivityStop();
//    }
//  }
//
//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    if (mWXSDKInstance != null) {
//      mWXSDKInstance.onActivityDestroy();
//    }
//  }
//
//  @Override
//  public void onViewCreated(WXSDKInstance instance, View view) {
//    if (view.getParent() != null) {
//      ((ViewGroup) view.getParent()).removeView(view);
//    }
//    mContainer.addView(view);
//  }
//
//  @Override
//  public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
//
//  }
//
//  @Override
//  public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
//
//  }
//
//  @Override
//  public void onException(WXSDKInstance instance, String errCode, String msg) {
//
//  }
//}
