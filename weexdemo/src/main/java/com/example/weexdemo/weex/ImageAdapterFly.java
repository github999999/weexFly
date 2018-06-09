package com.example.weexdemo.weex;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.weexdemo.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;


public class ImageAdapterFly implements IWXImgLoaderAdapter {
    @Override
    public void setImage(final String url, final ImageView view,
                         WXImageQuality quality, final WXImageStrategy strategy) {
        //实现你自己的图片下载，否则图片无法显示。
        String temp = url;
        if (url.startsWith("//")) {
            temp = "http:" + url;
            Log.e("ImageAdapterFly","temp = " + temp);
        }else {
            Log.e("ImageAdapterFly","else temp = " + temp);
        }

//        if (temp == null) {
//
//        }

        Picasso.with(WXEnvironment.getApplication())
                .load(temp)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("ImageAdapterFly","onSuccess() url = " + url);
                        if (strategy.getImageListener() != null) {
                            strategy.getImageListener().onImageFinish(url, view, true, null);
                        }

                        if (!TextUtils.isEmpty(strategy.placeHolder)) {
                            ((Picasso) view.getTag(strategy.placeHolder.hashCode())).cancelRequest(view);
                        }
                    }

                    @Override
                    public void onError() {
                        Log.e("ImageAdapterFly","onError() url = " + url);
//                        if (!TextUtils.isEmpty(url)) {
//                            if (url.contains("a_f")) {
//                                view.setImageResource(R.mipmap.navigation_item_back);
//                            }else if (url.contains("navigationItem_back")) {
//                                view.setImageResource(R.mipmap.navigation_item_back);
//                            }
//                        }

                        view.setImageResource(R.mipmap.ic_launcher);
                        if (strategy.getImageListener() != null) {
                            strategy.getImageListener().onImageFinish(url, view, false, null);
                        }
                    }
                });
    }
}
