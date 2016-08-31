package com.careers.parallaxeffectlib.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import com.careers.parallaxeffectlib.listeners.ScrollTabHolder;

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {
  protected ScrollTabHolder mScrollTabHolder;

  public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
    mScrollTabHolder = scrollTabHolder;
  }

  @Override
  public void onScroll(View view, int pagePosition) {
  }

  @Override
  public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
  }
}