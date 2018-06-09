package com.example.weexdemo.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * 加载本地图片
 */
public class ImageAdapterF implements IWXImgLoaderAdapter {

    public ImageAdapterF() {
    }

    @Override
    public void setImage(final String url, final ImageView view,
                         WXImageQuality quality, final WXImageStrategy strategy) {

        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(view==null||view.getLayoutParams()==null){
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                final String fixedUrl = temp;
//                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
//                    return;
//                }
                final String localTag = "http://local/";
                if (url.endsWith(".gif")) {
                    String fileName = url.substring(localTag.length());
                    Glide.with(WXEnvironment.getApplication())
                            .load("file:///android_asset/" + fileName)
                            .asGif()
                            .into(view);
                } else if(url.startsWith(localTag)) {   // 本地图片
                    //view.setimage
                    try {
                        String fileName = url.substring(localTag.length());
                        Picasso.with(WXEnvironment.getApplication())
                                .load("file:///android_asset/" + fileName)
                                .transform(new BlurTransformation(strategy.blurRadius))
                                .into(view, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        if(strategy.getImageListener()!=null) {
                                            strategy.getImageListener().onImageFinish(url,view,true,null);
                                        }

//                                        if(!TextUtils.isEmpty(strategy.placeHolder)){
//                                            ((Picasso) view.getTag(strategy.placeHolder.hashCode())).cancelRequest(view);
//                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        if(strategy.getImageListener()!=null){
                                            strategy.getImageListener().onImageFinish(url,view,false,null);
                                        }
                                    }
                                });

                    } catch (Throwable ex) {
                    }
                    return;
                }

                if (!TextUtils.isEmpty(strategy.placeHolder)) {
//                    Picasso.Builder builder = new Picasso.Builder(WXEnvironment.getApplication());
//                    Picasso picasso = builder.build();
//                    picasso.load(Uri.parse(strategy.placeHolder)).into(view);
//                    view.setTag(strategy.placeHolder.hashCode(), picasso);

                    // 正式图片加载失败后，再加载回默认图片  3
                    final Callback cb2 = new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            if(strategy.placeHolder.startsWith(localTag)){
                                String fileName = strategy.placeHolder.substring(localTag.length());
                                Picasso.with(WXEnvironment.getApplication())
                                        .load("file:///android_asset/" + fileName)
                                        .into(view);
                            } else {
                                Picasso.with(WXEnvironment.getApplication())
                                        .load(strategy.placeHolder)
                                        .into(view);
                            }
                        }
                    };

                    // 加载正式图片  2
                    final Callback cb = new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.with(WXEnvironment.getApplication())
                                    .load(fixedUrl)
                                    .transform(new BlurTransformation(strategy.blurRadius))
                                    .into(view, cb2);
                        }

                        @Override
                        public void onError() {
                            Picasso.with(WXEnvironment.getApplication())
                                    .load(fixedUrl)
                                    .transform(new BlurTransformation(strategy.blurRadius))
                                    .into(view, cb2);
                        }
                    };

                    // 先加载默认图片  1
                    if(strategy.placeHolder.startsWith(localTag)){
                        String fileName = strategy.placeHolder.substring(localTag.length());
                        Picasso.with(WXEnvironment.getApplication())
                                .load("file:///android_asset/" + fileName)
                                .into(view, cb);
                    } else {
                        Picasso.with(WXEnvironment.getApplication())
                                .load(strategy.placeHolder)
                                .into(view, cb);
                    }
                } else {
                    Picasso.with(WXEnvironment.getApplication())
                            .load(fixedUrl)
                            .transform(new BlurTransformation(strategy.blurRadius))
                            .into(view);
                }
            }
        },0);
    }
}
