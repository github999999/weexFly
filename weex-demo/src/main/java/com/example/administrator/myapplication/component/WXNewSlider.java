package com.example.administrator.myapplication.component;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXSlider;
import com.taobao.weex.ui.component.WXVContainer;

/**
 */
public class WXNewSlider extends WXSlider {

    @Deprecated
    public WXNewSlider(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, String instanceId) {
        this(instance, dom, parent);
    }

    public WXNewSlider(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }
}
