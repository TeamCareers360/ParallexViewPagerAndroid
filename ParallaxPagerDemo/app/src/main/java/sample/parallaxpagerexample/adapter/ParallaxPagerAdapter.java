package sample.parallaxpagerexample.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.careers.parallaxeffectlib.listeners.Constant;
import com.careers.parallaxeffectlib.listeners.OnFragmentSelectedListener;
import com.careers.parallaxeffectlib.listeners.ScrollTabHolder;

import sample.parallaxpagerexample.Constants;
import sample.parallaxpagerexample.R;
import sample.parallaxpagerexample.fragment.FirstFragment;
import sample.parallaxpagerexample.fragment.SecondFragment;
import sample.parallaxpagerexample.fragment.ThirdFragment;

/**
 * Created by ManuJaggi on 31-08-2016.
 */
public class ParallaxPagerAdapter extends FragmentPagerAdapter {

  public static final int MAX_SIZE = 3;  //max size of pager

  private Fragment[] fragments;
  private Context activity;
  private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
  private ScrollTabHolder mListener;


  public ParallaxPagerAdapter(FragmentManager fm, Context activity) {
    super(fm);
    this.activity = activity;
    mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();

    createFragmentArray();
  }


  public void setTabHolderScrollingContent(ScrollTabHolder listener) {
    mListener = listener;
  }

  public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
    return mScrollTabHolders;
  }


  @Override
  public Fragment getItem(int position) {

    Fragment fragment = null;

    if (fragments != null && fragments.length > position) {
      fragment = fragments[position];

      if (position == 0 && mListener != null) {
        ((FirstFragment) fragment).setScrollTabHolder(mListener);

      } else if (position == 1 && mListener != null) {
        ((SecondFragment) fragment).setScrollTabHolder(mListener);

      } else if (position == 2 && mListener != null) {
        ((ThirdFragment) fragment).setScrollTabHolder(mListener);

      }
    }

    ((OnFragmentSelectedListener)fragment).onFragmentSelected(activity.getResources().getDimensionPixelSize(R.dimen.parallax_header_height));

    return fragment;
  }

  @Override
  public int getCount() {
    return MAX_SIZE;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "Scroller";
      case 1:
        return "Fruit Scroller";
      case 2:
        return "Fruit List";


    }
    return super.getPageTitle(position);
  }

  /**
   * Use to create fragment array of static size{MAX_SIZE}.
   */
  private void createFragmentArray() {
    this.fragments = new Fragment[MAX_SIZE];
    for (int idx = 0; idx < fragments.length; ++idx) {

      Bundle bundle = new Bundle();
      switch (idx) {
        case 0:
          FirstFragment firstFragment = new FirstFragment();
          bundle.putInt(Constant.ARG_POSITION, idx);
          bundle.putInt(Constant.SCROLL_HEADER_HEIGHT, activity.getResources().getDimensionPixelSize(R.dimen
            .parallax_header_height));
          firstFragment.setArguments(bundle);
          mScrollTabHolders.put(idx, firstFragment);
          fragments[idx] = firstFragment;
          break;
        case 1:
          SecondFragment secondFragment = new SecondFragment();
          bundle.putInt(Constant.ARG_POSITION, idx);
          bundle.putInt(Constant.SCROLL_HEADER_HEIGHT, activity.getResources().getDimensionPixelSize(R.dimen
            .parallax_header_height));
          secondFragment.setArguments(bundle);
          mScrollTabHolders.put(idx, secondFragment);
          fragments[idx] = secondFragment;
          break;
        case 2:
          ThirdFragment thirdFragment = new ThirdFragment();
          bundle.putInt(Constant.ARG_POSITION, idx);
          bundle.putInt(Constant.SCROLL_HEADER_HEIGHT, activity.getResources().getDimensionPixelSize(R.dimen
            .parallax_header_height));
          thirdFragment.setArguments(bundle);
          mScrollTabHolders.put(idx, thirdFragment);
          fragments[idx] = thirdFragment;
          break;
      }
    }
  }

  /**
   * Call to get fragment of specified position.
   *
   * @param pos
   * @return instance of a fragment or null.
   */
  public Fragment getSelectedFragment(int pos) {
    if (fragments != null && fragments.length > pos) {
      return fragments[pos];
    }
    return null;
  }
}
