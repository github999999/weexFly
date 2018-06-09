package com.example.administrator.myapplication.network;

import android.content.Context;

import com.example.administrator.myapplication.util.ContextUtil;
import com.example.administrator.myapplication.network.retrofit.ByteArrayConverterFactory;
import com.example.administrator.myapplication.network.retrofit.StringConverterFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * HTTP网络连接器，初始化Context和创建网络栈
 * <p/>
 */
public class HttpConnector {

    private static boolean sDebug;

    public static void initNetworkConnector(Context context, boolean debug) {
        // init FiDu, FiDu与Retrofit共用同一个OkHttp线程池
        sDebug = debug;
    }

    // 静态内部类实现单例
    public static class InstanceHolder {
        private static final OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(new Cache(ContextUtil.getContext().getCacheDir(), 10 * 1024 * 1024))
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://eastmoney.com")
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(new ByteArrayConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    /**
     * 获取网络库使用的OkHttpClient
     */
    public static OkHttpClient getHttpClient() {
        return InstanceHolder.httpClient;
    }

}