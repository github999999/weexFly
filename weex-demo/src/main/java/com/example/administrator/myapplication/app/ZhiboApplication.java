package com.example.administrator.myapplication.app;

import android.app.Application;
import android.content.Context;

import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.network.HttpConnector;
import com.example.administrator.myapplication.util.ContextUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;


/**
 * Application
 */
public class ZhiboApplication extends Application {

    private static final String TAG = ZhiboApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        boolean debug = BuildConfig.DEBUG;

        // 初始化上下文工具类
        ContextUtil.init(this.getApplicationContext());
        HttpConnector.initNetworkConnector(this, debug);

        // fresco 配置
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config =
                OkHttpImagePipelineConfigFactory.newBuilder(this, HttpConnector.getHttpClient())
                        .setDownsampleEnabled(true)
                        .setRequestListeners(requestListeners)
                        .build();
        Fresco.initialize(this, config);

//        WeexConfigManager.initSdk(debug, this);
    }

}
