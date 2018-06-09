package com.example.weexdemo.extend.module;

import android.util.Log;
import android.widget.Toast;

import com.example.weexdemo.bean.EventCenter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import org.greenrobot.eventbus.EventBus;

public class MyModule extends WXModule {

    @JSMethod(uiThread = true)
    public void getLog(String msg){   // 这个方法是给weex调用的
//        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_LONG).show();
        EventBus.getDefault().post(new EventCenter("MyModule",msg));
    }

    @JSMethod(uiThread = false)
    public void setFlag(String msg){
        Log.e("MyModule = ",msg);
    }
}
