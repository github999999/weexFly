package com.example.administrator.myapplication.manager;

import android.app.Application;

import com.example.administrator.myapplication.adapter.FrescoImageAdapter;
import com.example.administrator.myapplication.adapter.PlayDebugAdapter;
import com.example.administrator.myapplication.adapter.WXOkHttpAdapter;
import com.example.administrator.myapplication.component.WXNewSlider;
import com.example.administrator.myapplication.component.view.WXSwipeRefreshView;
import com.example.administrator.myapplication.module.impl.WXEventModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
/**
 * weex 管理类
 */
public class WeexConfigManager {

    public static void initSdk(boolean debug, Application context) {
        InitConfig weexConfig = new InitConfig.Builder()
                .setHttpAdapter(new WXOkHttpAdapter())
                .setImgAdapter(new FrescoImageAdapter())   // 这个是必须配置的
                .setDebugAdapter(new PlayDebugAdapter())
                .build();
//        WXSDKEngine.initialize(debug, context, weexConfig);
        // 初始化Weex
        WXSDKEngine.initialize(context, weexConfig);
        try {
            // 注册module
            WXSDKEngine.registerModule("event", WXEventModule.class);
//            WXSDKEngine.registerModule("payModule", WXPayModule.class);
//            WXSDKEngine.registerModule("utilModule", WXUtilModule.class);
//            WXSDKEngine.registerComponent("image", FrescoImageComponent.class);
//            WXSDKEngine.registerComponent("clickview", ClickView.class);
            // 注册组件
            WXSDKEngine.registerComponent("slider", WXNewSlider.class);
//            WXSDKEngine.registerComponent("loadingbutton", WXLoadingButton.class);
//            WXSDKEngine.registerComponent("progresswheel", WXProgressWheel.class);
            WXSDKEngine.registerComponent("swiperefresh", WXSwipeRefreshView.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
