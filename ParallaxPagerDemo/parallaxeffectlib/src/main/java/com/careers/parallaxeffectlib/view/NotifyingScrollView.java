package com.careers.parallaxeffectlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.careers.parallaxeffectlib.listeners.Constant;


public class NotifyingScrollView extends ScrollView {


  public interface OnScrollChangedListener {
    void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
  }

  private OnScrollChangedListener mOnScrollChangedListener;

  public NotifyingScrollView(Context context) {
    super(context);
  }

  public NotifyingScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (mOnScrollChangedListener != null) {
      mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
    }
  }

  public void setOnScrollChangedListener(OnScrollChangedListener listener) {
//    Utils.printLog("ScrollValue", "YES");
    this.mOnScrollChangedListener = listener;
  }

//  @Override
//  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//    super.onSizeChanged(w, h, oldw, oldh);
//    Log.e(Constant.LOG_TAG," onSizeChanged ");
//  }
//
//  @Override
//  protected void onLayout(boolean changed, int l, int t, int r, int b) {
//    super.onLayout(changed, l, t, r, b);
//    if(changed){
//      Log.e(Constant.LOG_TAG," onLayout :- changed tag->" +tag);
//    }
//    Log.e(Constant.LOG_TAG," onLayout :- not changed tag-> "+tag);
//  }
//
//  String tag;
//
//  public void setTag(String tag){
//    this.tag=tag;
//  }
//
//  @Override
//  public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
//    super.updateViewLayout(view, params);
//    Log.e(Constant.LOG_TAG," updateViewLayout ");
//  }


}