package com.example.weexdemo.extend.compontent;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * 扩展圆形进度条
 */
public class CircleProgress extends WXComponent<Progress> {
    private int preColor = Color.parseColor("#f2f2f2");
    private int progressColor = Color.parseColor("#6bb849");
    private int CircleColor = Color.parseColor("#ffffff");
    private int textColor = Color.parseColor("#9bb879");
    public CircleProgress(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected Progress initComponentHostView(@NonNull Context context) {
        Progress progress = new Progress(context);
        progress.setTextSize(45).setTextColor(textColor).setCircleBackgroud(CircleColor)
                .setPreProgress(preColor).setProgress(progressColor)
                .setProdressWidth(8).setPaddingscale(0.8f);
        return progress;
    }

    @WXComponentProp(name = "progress")
    public void setProgress(String number) {
        if (!TextUtils.isEmpty(number)) {
            getHostView().setValue(Integer.parseInt(number));
        }
    }
}
