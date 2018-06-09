package com.example.weexdemo.extend.compontent;
import android.content.Context;

import com.example.weexdemo.R;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class GifImage extends WXComponent<GifImageView> {

    GifImageView gifview;
    Context context;
    public GifImage(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
    }

    @Override
    protected GifImageView initComponentHostView(Context context) {
        gifview = new GifImageView(context);
        this.context = context;
        return gifview;
    }

    @WXComponentProp(name = "src")//该注解，则为weex中调用的方法名
    public void setSrc(String src){
        try{
            GifDrawable gifFromAssets;
            if (src.contains("fly_fly")) {
                gifFromAssets = new GifDrawable( context.getResources(), R.drawable.loading);
            }else {
                gifFromAssets = new GifDrawable( context.getAssets(), src);
            }
            gifview.setImageDrawable(gifFromAssets);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}