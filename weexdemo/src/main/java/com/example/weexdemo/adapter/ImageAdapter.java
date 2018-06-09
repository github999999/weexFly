package com.example.weexdemo.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

import java.io.IOException;
import java.io.InputStream;

/**
 * 加载本地图片
 */
public class ImageAdapter implements IWXImgLoaderAdapter {

  private Bitmap getImageFromAssetsFile(String fileName, Context ctx)
  {
    Bitmap image = null;
    AssetManager am = ctx.getResources().getAssets();
    try
    {
      InputStream is = am.open(fileName);
      image = BitmapFactory.decodeStream(is);
      is.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return image;

  }

  /**
   * 设置图片
   * @param url   图片链接
   * @param view      图片控件
   * @param quality
   * @param strategy
   */
  @Override
  @JSMethod(uiThread = false)  // 不需要运行在UI线程中
  public void setImage(final String url, final ImageView view, WXImageQuality quality, final WXImageStrategy strategy) {
    //实现你自己的图片下载。
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
        if (temp.startsWith("/images/")) {
          //过滤掉所有相对位置
          temp = temp.replace("../", "");
          temp = temp.replace("./", "");
          //替换asset目录的配置
          temp = temp.replace("/images/", "file:///android_asset/weex/images/");
          Log.d("ImageAdapterFly", "url:" + temp);
        }
        if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
          return;
        }


        if(!TextUtils.isEmpty(strategy.placeHolder)){
          Picasso.Builder builder = new Picasso.Builder(WXEnvironment.getApplication());
          Picasso picasso = builder.build();
          picasso.load(Uri.parse(strategy.placeHolder)).into(view);

          view.setTag(strategy.placeHolder.hashCode(),picasso);
        }


        Picasso.with(WXEnvironment.getApplication())
                .load(temp)
                .into(view, new Callback() {
                  @Override
                  public void onSuccess() {
                    if(strategy.getImageListener()!=null){
                      strategy.getImageListener().onImageFinish(url,view,true,null);
                    }

                    if(!TextUtils.isEmpty(strategy.placeHolder)){
                      ((Picasso) view.getTag(strategy.placeHolder.hashCode())).cancelRequest(view);
                    }
                  }

                  @Override
                  public void onError() {
                    if(strategy.getImageListener()!=null){
                      strategy.getImageListener().onImageFinish(url,view,false,null);
                    }
                  }
                });
      }
    },0);
  }
}
