package com.careers.parallaxeffectlib.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.careers.parallaxeffectlib.BaseActivity;
import com.careers.parallaxeffectlib.R;
import com.careers.parallaxeffectlib.Utils;
import com.careers.parallaxeffectlib.listeners.Constant;
import com.careers.parallaxeffectlib.listeners.OnFragmentSelectedListener;

/**
 * Created by Abha Dhiman on 23-08-2016.
 * <p>Fragment with ListView, will handle the parallax effect for ViewPager.
 */
public class ParallaxListViewFragment extends ScrollTabHolderFragment implements AbsListView.OnScrollListener, OnFragmentSelectedListener {

  private boolean canScroll = true;
  private int mListViewHeight;
  private int mActionBarHeight;
  private int prevHeaderHeight;
  protected int sHeight;
  private int screenHeight;

  protected int mPosition;
  protected int headerHeight;
  protected boolean largeContentAvailable;

  private BaseActivity activity;
  private ListView mListView;
  private LinearLayout mListHeader;
  private ListAdapter adapter;
  private int scrollState;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    if (args != null) {
      mPosition = args.getInt(Constant.ARG_POSITION);
      headerHeight = args.getInt(Constant.SCROLL_HEADER_HEIGHT);
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
    mListView = (ListView) view.findViewById(R.id.notifying_list_view);

    // set default header
    if(mListView.getHeaderViewsCount() == 0) {
      mListHeader = new LinearLayout(activity);
      mListHeader.setOrientation(LinearLayout.HORIZONTAL);
      mListHeader.setBackgroundColor(Color.TRANSPARENT);
      mListHeader.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT));
      mListHeader.setPadding(0, headerHeight - mListView.getDividerHeight(), 0, 0);

      mListView.addHeaderView(mListHeader);
    }
    mListView.setOnScrollListener(this);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
  }

  @Override
  public void adjustScroll(int scrollHeight, int headerTranslationY) {

    if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
      return;
    }

    checkForLargeContent();

    if (adapter == null)
      adapter = mListView.getAdapter();

//    if (adapter != null && (adapter.getCount() - mListView.getHeaderViewsCount() > 0))  {
    if (adapter != null )  {
      if (largeContentAvailable) {


        if (this.mListViewHeight - (headerTranslationY - scrollHeight) > screenHeight - Utils.getActionBarHeight(activity, activity.getTheme())) {

          mListHeader.setPadding(0, headerHeight - mListView.getDividerHeight(), 0, 0);
          canScroll = true;
          mListView.setSelectionFromTop(1, scrollHeight);
        } else {

          canScroll = false;
          mListHeader.setPadding(0, headerHeight - mListView.getDividerHeight() - (headerTranslationY - scrollHeight), 0, 0);
        }
      } else {

        canScroll = false;
        mListHeader.setPadding(0, headerHeight - mListView.getDividerHeight() - (headerTranslationY - scrollHeight), 0, 0);
      }
    } else {

      canScroll = false;
      prevHeaderHeight = headerTranslationY;
      sHeight = scrollHeight;
    }

    prevHeaderHeight = headerTranslationY;
    sHeight = scrollHeight;

  }

  /**
   * Method used to Check whether ListView contains content to scroll enough..
   */
  private void checkForLargeContent() {
    screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
    mActionBarHeight = Utils.getActionBarHeight(activity, activity.getTheme());
    mListViewHeight = getListViewHeight();

    if (mListHeader.getPaddingTop() < headerHeight) {
      mListViewHeight = mListViewHeight - mListHeader.getPaddingTop() + headerHeight;
    }

    if (mListViewHeight > (screenHeight - mActionBarHeight)) {
      largeContentAvailable = true;
    } else {
      largeContentAvailable = false;
    }

  }

  /**
   * @return : method will evaluate and return the height of ListView Content.
   */
  private int getListViewHeight() {
    if (mListView == null)
      return 0;

    if (adapter == null)
      adapter = mListView.getAdapter();

    if (adapter == null)
      return 0;

    int size = adapter.getCount() - mListView.getHeaderViewsCount();

    int headerCount = mListView.getHeaderViewsCount();
    int height = 0;
    for (int i = 0; i < headerCount; i++) {
      View childAt = mListView.getChildAt(i);
      if (childAt != null) {
        if (i % headerCount == 0) {
          height += headerHeight;
        } else {
          childAt.measure(0, 0);
          height += childAt.getMeasuredHeight();
        }
      }
    }

    for (int itemPos = 0; itemPos < size; itemPos++) {
      if (height >= screenHeight + Utils.getActionBarHeight(activity, activity.getTheme())) {
        break;
      }else {
        View item = adapter.getView(itemPos, null, mListView);
        item.measure(0, 0);
        height += item.getMeasuredHeight();
      }
    }

    // Get total height of all item dividers.
    int totalDividersHeight = mListView.getDividerHeight() * (size - 1);
    height += totalDividersHeight;

    return height;
  }



  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {

    this.scrollState=scrollState;

  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    if (mScrollTabHolder != null && canScroll) {
      mScrollTabHolder.onScroll(view, mPosition);
    }
    getScrolledHeight(view);
  }


  /**
   * @param view : Reference of list view.
   * <p>Method will evaluate that how much ListView scrolled upwards or downwards </p>
   */
  private void getScrolledHeight(AbsListView view) {
    int scrollY = Utils.getScrollY(view,headerHeight);
    if (scrollState != SCROLL_STATE_IDLE) {
      sHeight = Math.max(-scrollY, activity.mMinHeaderTranslation) + prevHeaderHeight;

    }
  }


  /**
   * <p>Method will be used in child fragment whenenver List View content will dynamically change. It will recalculate & adjust the listview</p>
   */
  public void evaluateHeightAfterLoading() {

    adjustScroll(sHeight, prevHeaderHeight);
  }


  @Override
  public void onFragmentSelected(int lHeaderHeight) {
    if (headerHeight == 0) {
      headerHeight = lHeaderHeight;
      if (mListHeader != null)
        mListHeader.setPadding(0, headerHeight - mListView.getDividerHeight(), 0, 0);
    }
  }
}
