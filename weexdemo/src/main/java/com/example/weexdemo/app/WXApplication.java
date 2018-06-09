package com.example.weexdemo.app;
import android.app.Application;
import android.content.Context;

import com.example.weexdemo.adapter.ImageAdapter;
import com.example.weexdemo.R;
import com.example.weexdemo.adapter.ImageAdapterF;
import com.example.weexdemo.extend.compontent.CircleProgress;
import com.example.weexdemo.extend.compontent.GifImage;
import com.example.weexdemo.extend.module.MyModule;
import com.example.weexdemo.extend.module.PhoneInfoModule;
import com.example.weexdemo.refresh.WXSwipeRefreshView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
/**
 * 注意要在Manifest中启用
 * 参考manifest，否则会抛出ExceptionInInitializerError
 * 要实现ImageAdapter 否则图片不能下载
 * gradle 中一定要添加一些依赖，否则初始化会失败。
 * compile 'com.android.support:recyclerview-v7:23.1.1'
 * compile 'com.android.support:support-v4:23.1.1'
 * compile 'com.android.support:appcompat-v7:23.1.1'
 * compile 'com.alibaba:fastjson:1.1.45'
 */
public class WXApplication extends Application {

  static {
    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
      @Override
      public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
        return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
      }
    });
    //设置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
      @Override
      public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        //指定为经典Footer，默认是 BallPulseFooter
        return new ClassicsFooter(context).setDrawableSize(20);
      }
    });
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Logger.addLogAdapter(new AndroidLogAdapter());
    InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapterF()).build();
//    InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
//    InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapterFly()).build();
    WXSDKEngine.initialize(this, config);
    try {
      WXSDKEngine.registerModule("poneInfo", PhoneInfoModule.class);
      WXSDKEngine.registerModule("MyModule", MyModule.class);
//      WXSDKEngine.registerComponent("rich", RichText.class, false);
//      WXSDKEngine.registerComponent("circle", CircleProgress.class);
//      WXSDKEngine.registerComponent("swiperefresh", WXSwipeRefreshView.class);
      WXSDKEngine.registerComponent("gifimage", GifImage.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
  }
}
