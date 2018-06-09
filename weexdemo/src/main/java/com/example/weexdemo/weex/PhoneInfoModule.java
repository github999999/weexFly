//package com.example.weexdemo.weex;
//
//import android.os.Build;
//import android.widget.Toast;
//
//import com.taobao.weex.bridge.JSCallback;
//import com.taobao.weex.common.WXModule;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2018/4/27.
// */
//
//public class PhoneInfoModule extends WXModule {
//
//    /**
//     * 获取 Phone 相关信息，并回调给 weex
//     */
//    @JSMethod(uiThread = false)
//    public void getPhoneInfo(JSCallback callback) {   // JSCallback 为 WeexSDK 中的 API
//        Map<String, String> infos = new HashMap<>();
//        infos.put("board", Build.BOARD);
//        infos.put("brand", Build.BRAND);
//        infos.put("device", Build.DEVICE);
//        infos.put("model", Build.MODEL);
//        callback.invoke(infos);
//    }
//
//    @JSMethod(uiThread = true)
//    public void printLog() {
//        Toast.makeText(mWXSDKInstance.getContext(), "item clicked", Toast.LENGTH_SHORT).show();
//    }
//}
