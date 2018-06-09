//package com.example.weexdemo.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//
//import com.example.weexdemo.R;
//import com.example.weexdemo.base.WXBaseActivity;
//import com.example.weexdemo.bean.EventCenter;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.taobao.weex.WXSDKInstance;
//
//import org.greenrobot.eventbus.Subscribe;
//
//public class NetworkActivityCopy extends WXBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
//    private FrameLayout mContainer;
//    private static final String TAG = NetworkActivityCopy.class.getSimpleName();
//    private static final String JS_PATH = "http://192.168.2.102:9999?page=index";
//    private RefreshLayout refreshLayout;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//ImageView RelativeLayout_top;
//    private TranslateAnimation mHiddenAction;
//    private TranslateAnimation mShowAction;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_network);
//        mContainer = findViewById(R.id.container);
//        setViewGlobalLayoutListener(mContainer);
//        RelativeLayout_top = findViewById(R.id.RelativeLayout_top);
//        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
//                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f);
//        mHiddenAction.setDuration(200);
//        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//        mShowAction.setDuration(200);
////        refreshLayout = findViewById(R.id.refreshLayout);
//        mSwipeRefreshLayout = findViewById(R.id.refreshLayout);
////        // 设置刷新时进度动画变换的颜色，接收参数为可变长度数组。也可以使用setColorSchemeColors()方法。
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R
//                .color.holo_green_light);
//        // 设置刷新时圆形图标的大小。只可传递2个参数，SwipeRefreshLayout.LARGE或SwipeRefreshLayout.DEFAULT。
//        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
//        // 设置刷新时圆形图标的背景色。也可以使用setProgressBackgroundColorSchemeColor()方法。
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.background_light);
//        // 设置刷新时的圆形图标。scale：是否缩放；start：圆形图标在刷新前的起始位置；end：圆形图标的偏移量。
//        mSwipeRefreshLayout.setProgressViewOffset(true, 100, 200);
//        // 设置会触发下拉刷新的手势滑动距离
////        mSwipeRefreshLayout.setDistanceToTriggerSync(500);
//        // 设置刷新的监听事件
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//
////    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
////      @Override
////      public void onRefresh(@NonNull RefreshLayout refreshlayout) {
//////        refreshlayout.finishRefresh(20/*,false*/);//传入false表示刷新失败
////        refreshlayout.finishRefresh(0);//传入false表示刷新失败
//////          refreshlayout.srlReboundDuration();
////        refreshLayout.setEnableRefresh(false);
////      }
////    });
////
////    refreshLayout.setEnableLoadMore(false);
////        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
////        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
////        refreshLayout.setEnableOverScrollDrag(false);//是否启用越界拖动（仿苹果效果）1.0.4
////        refreshLayout.setDisableContentWhenRefresh(false);//是否在刷新的时候禁止列表的操作
////        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//设置Header
////    refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
////      @Override
////      public void onLoadMore(RefreshLayout refreshlayout) {
////        refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
////      }
////    });
//    }
//
//    @Override
//    public void onViewCreated(WXSDKInstance instance, View view) {
//        if (view != null && mContainer != null && view.getParent() == mContainer) {
//            mContainer.removeView(view);
//        }
//
//        if (mContainer != null) {
//            mContainer.removeAllViews();
//            mContainer.addView(view);
//            mContainer.requestLayout();
//        }
//    }
//
//    @Override
//    protected boolean isEventBus() {
//        return true;
//    }
//
//    int type;  // 1 可以刷新
//    @Subscribe
//    public void mainEventThread(EventCenter eventCenter) {
//        switch (eventCenter.getType()) {
//            case "MyModule":
//                if (TextUtils.equals("refresh", (String) eventCenter.getData())) {
//                    Log.e(TAG, "可以刷新");
//                    type = 1;
////          refreshLayout.setEnableRefresh(true);
//                } else {
//                    Log.e(TAG, "不能刷新");
//                    type = 2;
//                    mSwipeRefreshLayout.setEnabled(false);
////          refreshLayout.setEnableRefresh(false);
//                }
//                break;
//        }
//    }
//
//    int y = 0;
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                y = (int) ev.getY();
//                Log.e(TAG, "dispatchTouchEvent action:ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int y1 = (int) ev.getY();
//                Log.e("hahahahahaahahaahah哈哈哈哈","y1 = " + y1 + " --->  y = " + y);
//                Log.e("hahahahahaahahaahah哈哈哈哈","type = " + type);
//                if ((y1 - y >300) && (type == 1)) {
////                    RelativeLayout_top.startAnimation(mShowAction);
//                    RelativeLayout_top.setVisibility(View.VISIBLE);
//                    Log.e("显示","type = " + type);
//                }else if (Math.abs(y1 - y) <= 80) {
//
//                }else if ((y1 - y >500) && (type == 1)) {
//                    mSwipeRefreshLayout.setEnabled(true);
//                }else{
//                    Log.e("隐藏","type = " + type);
////                    RelativeLayout_top.startAnimation(mHiddenAction);
//                    RelativeLayout_top.setVisibility(View.GONE);
//                    type = 2;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            type = 1;
//                        }
//                    },200);
//                }
//                Log.e(TAG, "dispatchTouchEvent action:ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "dispatchTouchEvent action:ACTION_UP");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e(TAG, "dispatchTouchEvent action:ACTION_CANCEL");
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    protected String getPageName() {
//        return TAG;
//    }
//
//    @Override
//    protected String getAssembleFilePath() {
//        return JS_PATH;
//    }
//
//    @Override
//    protected void refreshWithJsFile(String filePath) {
//
//    }
//
//    @Override
//    protected ViewGroup getContainerLayout() {
//        return mContainer;
//    }
//
//    @Override
//    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
//    }
//
//    @Override
//    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
//    }
//
//    @Override
//    public void onException(WXSDKInstance instance, String errCode,
//                            String msg) {
//        super.onException(instance, errCode, msg);
//    }
//
//    @Override
//    public void onRefresh() {
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//}
