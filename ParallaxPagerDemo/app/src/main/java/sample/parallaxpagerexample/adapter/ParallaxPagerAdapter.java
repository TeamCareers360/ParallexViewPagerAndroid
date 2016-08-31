package sample.parallaxpagerexample.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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


  public ParallaxPagerAdapter(FragmentManager fm, Context activity) {
    super(fm);
    this.activity = activity;

    createFragmentArray();
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment = null;

    if (fragments != null && fragments.length > position) {
      fragment = fragments[position];
    }
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
        return "First";
      case 1:
        return "Second";
      case 2:
        return "Third";


    }
    return super.getPageTitle(position);
  }

  /**
   * Use to create fragment array of static size{MAX_SIZE}.
   */
  private void createFragmentArray() {
    this.fragments = new Fragment[MAX_SIZE];
    for (int idx = 0; idx < fragments.length; ++idx) {
      Fragment fragment = null;
      Bundle bundle = new Bundle();

      switch (idx) {
        case 0:
          fragment = new FirstFragment();
          break;
        case 1:
          fragment = new SecondFragment();
          break;
        case 2:
          fragment = new ThirdFragment();
          break;

      }
      if (fragment != null) {
//        bundle.putInt(Constants.POSITION, idx);
//        bundle.putAll(this.bundle);
//        fragment.setArguments(bundle);
      }
      fragments[idx] = fragment;
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
