package com.careers.parallaxeffectlib.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.careers.parallaxeffectlib.BaseActivity;
import com.careers.parallaxeffectlib.listeners.Constant;
import com.careers.parallaxeffectlib.R;
import com.careers.parallaxeffectlib.listeners.OnFragmentSelectedListener;
import com.careers.parallaxeffectlib.view.NotifyingScrollView;

/**
 * Created by Abha on 26-01-2016.
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
  protected int scrollHeaderHeight;
  // will evaluate action bar height in case of ExamViewGuidanceFragment
//  protected boolean isGuidanceFragment = false;
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
      Log.e(Constant.LOG_TAG, "ParallexScrollViewFragment onCreate() scrollHeaderHeight : " + scrollHeaderHeight);
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
    Log.e(Constant.LOG_TAG, "onViewCreated()");
    mScrollView = (NotifyingScrollView) view.findViewById(R.id.notifying_scroll_view);

    DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

    screenHeight = displayMetrics.heightPixels;

    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.AT_MOST);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    mScrollView.setOnScrollChangedListener(this);

    View childAt = mScrollView.getChildAt(0);
    if (childAt != null) {
      childAt.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
          if (bottom != oldBottom) {
            Log.e(Constant.LOG_TAG, " *** onLayoutChanged *** " + "bottom - " + bottom + "oldBottom - " + oldBottom);
            isChildLayoutUpdated = true;
            recalculateScroll();

          }
        }

      });
    }

    mHomeProxieLayout = (View) view.findViewById(R.id.proxie_home_header);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
//    activity = (BaseActivity) getActivity();

    Log.e(Constant.LOG_TAG, "onActivityCreated()");
    Log.e(Constant.LOG_TAG, "ParallexScrollViewFragment scrollHeaderHeight : " + scrollHeaderHeight);
    mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e(Constant.LOG_TAG, "onResume mPosition : " + mPosition + " mHomeProxieLayout.height : " + mHomeProxieLayout.getLayoutParams().height);
    recalculateScroll();
  }


  /**
   * <p>Method will set the scrolled postion to scroll view and header height of screen</p>
   */
  private void recalculateScroll() {
    if (isChildLayoutUpdated) {
//      int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
      mActionBarHeight = getActionBarHeight();
      int localScrollHeight = getScrollViewHeight();

      localScrollHeight = localScrollHeight - mHomeProxieLayout.getLayoutParams().height + scrollHeaderHeight;

      if (localScrollHeight > (screenHeight - mActionBarHeight)) {
        Log.e(Constant.LOG_TAG, "localScreenHeight : " + localScrollHeight);

        if (localScrollHeight - (mLastScrollY) > screenHeight - getActionBarHeight()) {
          Log.e(Constant.LOG_TAG, "case 1 a");
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
          Log.e(Constant.LOG_TAG, "case 1 b ");
          mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (mPrevScrollHeight)));
          canScroll = false;
        }
      } else {
        Log.e(Constant.LOG_TAG, "case 2 ");
        checkForLargeContent();
        canScroll = true;
      }
      isChildLayoutUpdated = false;
    } else {
      Log.e(Constant.LOG_TAG, "layout does not updated");
      canScroll = true;
//      if (isGuidanceFragment) {
//        calForGuidanceFragment();
//      }
    }
    checkForLargeContent();
  }

  /*private void calForGuidanceFragment() {
    Log.e(Constant.LOG_TAG, " mPosition : " + mPosition + " isGuidanceFragment : " + isGuidanceFragment);
    int screenHeight = getResources().getDisplayMetrics().heightPixels;
    mActionBarHeight = getActionBarHeight();
    int localScrollHeight = getScrollViewHeight();

    localScrollHeight = localScrollHeight - mHomeProxieLayout.getLayoutParams().height + scrollHeaderHeight;

    if (localScrollHeight > (screenHeight - mActionBarHeight)) {
      Log.e(Constant.LOG_TAG, "calForGuidanceFragment localScreenHeight : " + localScrollHeight);

      if (localScrollHeight - (mLastScrollY) > screenHeight - getActionBarHeight()) {
        Log.e(Constant.LOG_TAG, "calForGuidanceFragment case 1 a");
        canScroll = true;

      } else {
        Log.e(Constant.LOG_TAG, "calForGuidanceFragment case 1 b ");
        canScroll = false;
      }
    } else {
      Log.e(Constant.LOG_TAG, "calForGuidanceFragment case 2 ");
      canScroll = false;
    }
  }*/

  /**
   * Check wethear scroll view contains the scrollable content or not.
   */
  private void checkForLargeContent() {
//    int screenHeight = getResources().getDisplayMetrics().heightPixels;
    mActionBarHeight = getActionBarHeight();
    mScrollHeight = getScrollViewHeight();

    if (mHomeProxieLayout.getLayoutParams().height < scrollHeaderHeight) {
      Log.e(Constant.LOG_TAG, "checkForLArgeContent : INSIDE HEIGHT CHECK");
      mScrollHeight = mScrollHeight - mHomeProxieLayout.getLayoutParams().height + scrollHeaderHeight;
    }

    if (mScrollHeight > (screenHeight - mActionBarHeight)) {
      largeContentAvailable = true;
    } else {
      largeContentAvailable = false;
    }

    Log.e(Constant.LOG_TAG, "checkForLargeContent() : scrollview_total height : " + mScrollHeight + " actionBarHeight : " + mActionBarHeight + " screenHeight : " + screenHeight + " largeContentAvailable : " + largeContentAvailable);
  }

  @Override
  public void adjustScroll(final int scrollHeight, final int headerTranslationY) {
    if (scrollHeight == 0) {
      screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
    }

    Log.e(Constant.LOG_TAG, "AdjustScroll");
    Log.e(Constant.LOG_TAG, "Scroll Height " + scrollHeight);
    Log.e(Constant.LOG_TAG, "headerTranslationY " + headerTranslationY);
    Log.e(Constant.LOG_TAG, "this.mScrollHeight - (headerTranslationY - mScrollHeight) " + (this.mScrollHeight - (headerTranslationY - scrollHeight)));
    Log.e(Constant.LOG_TAG, "screenHeight - getActionBarHeight() " + (screenHeight - getActionBarHeight()));

    if (largeContentAvailable) {
      Log.e(Constant.LOG_TAG, "LargeContent Available");
      //checks whether content can scroll to (headerTranslationY - mScrollHeight) which is headerTranslationValue
      Log.e(Constant.LOG_TAG, "Check " + (this.mScrollHeight > screenHeight - getActionBarHeight()));

      int scrollingHeight = this.mScrollHeight - (headerTranslationY - scrollHeight);

      if (scrollingHeight > screenHeight - getActionBarHeight()) {
        Log.e(Constant.LOG_TAG, "CHECK 1");
        //down scroll
        if (mLastScrollY >= (headerTranslationY - scrollHeight) && mPrevScrollHeight <= screenHeight) {
          // During scrolling down ,set the view height to initialHeight and set scrollY as content is scrollable.
          Log.e(Constant.LOG_TAG, "CHECK DOWN SCROLL last y : " + mLastScrollY + " y : " + (headerTranslationY - scrollHeight));
          mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
          mHomeProxieLayout.postInvalidate();
          Log.e(Constant.LOG_TAG, "Invalidate View ");
          canScroll = true;
          //setLayoutParams takes some time to update so setScrollY after some time
          mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
              Log.e(Constant.LOG_TAG, "Scroll View setScrollY()");
              mScrollView.setScrollY(headerTranslationY - scrollHeight);
            }
          }, 10);

        } else {
          // During scrolling Up ,as content is scrollable to just set the scroll y to scrollView
          Log.e(Constant.LOG_TAG, "CHECK UP SCROLL last y : " + mLastScrollY + " y : " + (headerTranslationY - scrollHeight));
          canScroll = true;
          mScrollView.setScrollY(headerTranslationY - scrollHeight);
        }
      } else {
        //content cannot scroll to (headerTranslationY - mScrollHeight) which is headerTranslationValue so just set the params to view
        Log.e(Constant.LOG_TAG, "CHECK 2");
        mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (scrollHeight)));
        canScroll = false;
      }
    } else {
      // Small content
      Log.e(Constant.LOG_TAG, " Large content is not available");
      mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (scrollHeight)));
      canScroll = false;
    }

    mLastScrollY = headerTranslationY - scrollHeight;
    mPrevScrollHeight = scrollHeight;
    Log.e(Constant.LOG_TAG, "canScroll : " + canScroll);
  }

  @Override
  public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {

    if (mScrollTabHolder != null && canScroll) {
      mScrollTabHolder.onScroll(who, mPosition);
    }
  }


  private int getActionBarHeight() {
    if (mActionBarHeight != 0) {
      Log.e(Constant.LOG_TAG, " mActionBar height " + mActionBarHeight);
      return mActionBarHeight;
    }
    Log.e(Constant.LOG_TAG, " evaluate action bar again ");
    mActionBarHeight = activity.getMinParallexHeaderHeight();
    if (mActionBarHeight == 0) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
        activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
      } else {
        activity.getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
      }
      mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, activity.getResources().getDisplayMetrics());
    }
    return mActionBarHeight;
  }

  protected int getScrollViewHeight() {
    View childAt = mScrollView.getChildAt(0);
    childAt.measure(widthMeasureSpec, heightMeasureSpec);
    return childAt.getMeasuredHeight();
  }

  // call this method in child fragment if child fragment ui updated before onResume.
//  protected void childFragmentUpdated(boolean isChildLayoutUpdated) {
//    Log.e(Constant.LOG_TAG, "childLayoutUpdated");
//    this.isChildLayoutUpdated = isChildLayoutUpdated;
//  }

  // call this method in child fragment if child fragment ui updated before onResume.
  // this method is used in CollegeView cut off fragment
//  protected void shouldEvaluateHeightAgain(boolean isEvaluate) {
//    Log.e(Constant.LOG_TAG, "shouldEvaluateHeightAgain & fragment position " + mPosition);
//    if (isEvaluate) {
//      this.isChildLayoutUpdated = isEvaluate;
//      recalculateScroll();
//    }
//  }

  @Override
  public void onFragmentSelected(int lHeaderHeight) {
    if (scrollHeaderHeight == 0) {
      Log.e(Constant.LOG_TAG, " on Parallax Scroll View fragment : " + lHeaderHeight);
      scrollHeaderHeight = lHeaderHeight;
      if (mHomeProxieLayout != null) {
        mHomeProxieLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, scrollHeaderHeight));
      }
    }
  }
}
