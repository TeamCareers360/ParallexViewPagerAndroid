
package com.careers.parallaxeffectlib.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.careers.parallaxeffectlib.BaseActivity;
import com.careers.parallaxeffectlib.R;
import com.careers.parallaxeffectlib.Utils;
import com.careers.parallaxeffectlib.listeners.Constant;
import com.careers.parallaxeffectlib.listeners.OnFragmentSelectedListener;
import com.careers.parallaxeffectlib.view.NotifyingScrollView;


/**
 * Created by Abha on 26-01-2016.
 * <p>Fragment with ScrollView, will handle the parallax effect for ViewPager.
 */

public abstract class ParallaxScrollViewFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener, OnFragmentSelectedListener {

  private static final String LOG_TAG = ParallaxScrollViewFragment.class.getSimpleName();

  protected NotifyingScrollView mScrollView;
  protected View mHomeProxieLayout;
  protected boolean canScroll = true;
  protected boolean largeContentAvailable;
  private boolean isChildLayoutUpdated = false;

  private int mActionBarHeight;
  private TypedValue mTypedValue = new TypedValue();
  protected int mScrollHeight;

  private int mLastScrollY;
  private int mPrevScrollHeight;
  protected int mPosition;
  private int scrollHeaderHeight;

  private BaseActivity activity;
  private int widthMeasureSpec;
  private int heightMeasureSpec;
  private int screenHeight;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    if (args != null) {
      mPosition = args.getInt(Constant.ARG_POSITION);
      if (scrollHeaderHeight == 0) {
        scrollHeaderHeight = args.getInt(Constant.SCROLL_HEADER_HEIGHT);
      }
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof BaseActivity) {
      activity = (BaseActivity) context;
    }
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mScrollView = (NotifyingScrollView) view.findViewById(R.id.notifying_scroll_view);

    mScrollView.setOnScrollChangedListener(this);

    // Perform calculation after layout change in ScrollView
    View childAt = mScrollView.getChildAt(0);
    if (childAt != null) {
      childAt.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
          if (bottom != oldBottom) {
            isChildLayoutUpdated = true;
            evaluateScrollHeight();
          }
        }

      });
    }

    mHomeProxieLayout = (View) view.findViewById(R.id.proxie_home_header);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
    screenHeight = displayMetrics.heightPixels;
    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.AT_MOST);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);


    mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
  }

  @Override
  public void onResume() {
    super.onResume();
    evaluateScrollHeight();
  }


  /**
   * <p>Method will evaluate & adjust the the scrolled postion to scroll view and header height of screen</p>
   */
  private void evaluateScrollHeight() {
    if (isChildLayoutUpdated) {
      mActionBarHeight = Utils.getActionBarHeight(activity, activity.getTheme());
      int localScrollHeight = getScrollViewHeight();

      localScrollHeight = localScrollHeight - mHomeProxieLayout.getLayoutParams().height + scrollHeaderHeight;

      if (localScrollHeight > (screenHeight - mActionBarHeight)) {

        if (localScrollHeight - (mLastScrollY) > screenHeight - Utils.getActionBarHeight(activity, activity.getTheme())) {

          mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
          mHomeProxieLayout.postInvalidate();
          canScroll = true;
          mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
              mScrollView.setScrollY(mLastScrollY);
            }
          }, 10);

        } else {
          mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (mPrevScrollHeight)));
          canScroll = false;
        }
      } else {

        checkForLargeContent();
        canScroll = true;
      }
      isChildLayoutUpdated = false;
    } else {
      canScroll = true;
    }
    checkForLargeContent();
  }


  /**
   * Method used to Check whether scroll view contains the scrollable content or not.
   */
  private void checkForLargeContent() {

    mActionBarHeight = Utils.getActionBarHeight(activity, activity.getTheme());
    mScrollHeight = getScrollViewHeight();

    if (mHomeProxieLayout.getLayoutParams().height < scrollHeaderHeight) {
      mScrollHeight = mScrollHeight - mHomeProxieLayout.getLayoutParams().height + scrollHeaderHeight;
    }

    if (mScrollHeight > (screenHeight - mActionBarHeight)) {
      largeContentAvailable = true;
    } else {
      largeContentAvailable = false;
    }
  }

  @Override
  public void adjustScroll(final int scrollHeight, final int headerTranslationY) {

    if (screenHeight == 0) {
      screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
    }

    if (largeContentAvailable) {

      //checks whether content can scroll to (headerTranslationY - mScrollHeight) which is headerTranslationValue
      int scrollingHeight = this.mScrollHeight - (headerTranslationY - scrollHeight);

      if (scrollingHeight > screenHeight - Utils.getActionBarHeight(activity, activity.getTheme())) {
        //down scroll
        if (mLastScrollY >= (headerTranslationY - scrollHeight) && mPrevScrollHeight <= screenHeight) {

          // During scrolling down ,set the view height to initialHeight and set scrollY as content is scrollable.
          mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
          mHomeProxieLayout.postInvalidate();
          canScroll = true;

          //setLayoutParams takes some time to update so setScrollY after some time
          mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
              mScrollView.setScrollY(headerTranslationY - scrollHeight);
            }
          }, 10);

        } else {
          // During scrolling Up ,as content is scrollable to just set the scroll y to scrollView
          canScroll = true;
          mScrollView.setScrollY(headerTranslationY - scrollHeight);
        }
      } else {

        //content cannot scroll to (headerTranslationY - mScrollHeight) which is headerTranslationValue so just set the params to view
        mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (scrollHeight)));
        canScroll = false;
      }
    } else {

      // Small content
      mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (scrollHeight)));
      canScroll = false;
    }

    mLastScrollY = headerTranslationY - scrollHeight;
    mPrevScrollHeight = scrollHeight;
  }

  @Override
  public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {

    if (mScrollTabHolder != null && canScroll) {
      mScrollTabHolder.onScroll(who, mPosition);
    }
    getScrolledHeight(who);
  }

  /**
   * @param view : Reference of NotifyingScrollView
   * <p>Method used to evaluate the scroll height and scroll y position after scrolling the content of scroll view</p>
   */
  private void getScrolledHeight(ScrollView view) {
    int scrollY = view.getScrollY();
    if (scrollY != 0) {
      mPrevScrollHeight = Math.max(-scrollY, activity.mMinHeaderTranslation) + scrollHeaderHeight;
      mLastScrollY = scrollHeaderHeight - mPrevScrollHeight;
    }
  }

  /**
   * @return : method will return the ScrollView Height.
   */
  protected int getScrollViewHeight() {
    View childAt = mScrollView.getChildAt(0);
    childAt.measure(widthMeasureSpec, heightMeasureSpec);
    return childAt.getMeasuredHeight();
  }

  @Override
  public void onFragmentSelected(int lHeaderHeight) {

    if (scrollHeaderHeight == 0) {
      scrollHeaderHeight = lHeaderHeight;
      if (mHomeProxieLayout != null) {
        mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
      }
    }
  }

}



