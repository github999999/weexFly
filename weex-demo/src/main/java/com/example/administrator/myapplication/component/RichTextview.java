package com.example.administrator.myapplication.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * 自定义 富文本
 */
public class RichTextview extends WXComponent {

    public RichTextview(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
    }

    @Override
    protected TextView initComponentHostView(@NonNull Context context) {
        TextView view = new TextView(context);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    @WXComponentProp(name = "tel")
    public void setTelLink(String tel){
        SpannableString spannable=new SpannableString(tel);
        spannable.setSpan(new URLSpan("tel:"+tel),0,tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView)getHostView()).setText(spannable);
    }
}
