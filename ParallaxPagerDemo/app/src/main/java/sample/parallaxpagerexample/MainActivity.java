package sample.parallaxpagerexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.careers.parallaxeffectlib.BaseActivity;
import com.careers.parallaxeffectlib.Utils;
import com.careers.parallaxeffectlib.listeners.OnFragmentSelectedListener;
import com.careers.parallaxeffectlib.listeners.ScrollTabHolder;
import com.nineoldandroids.view.ViewHelper;

import sample.parallaxpagerexample.adapter.ParallaxPagerAdapter;
import sample.parallaxpagerexample.uicomponent.SlidingTabLayout;

public class MainActivity extends BaseActivity implements ScrollTabHolder {

  public static int TAB_VIEW_PADDING_LEFT_RIGHT;
  public static int SELECTED_INDICATOR_THICKNESS_DIPS;
  private SlidingTabLayout slidingTabLayout;
  private Toolbar toolbar;
  private ViewPager viewPager;
  private RelativeLayout parallaxHeader;
  private int headerHeight;
  private ParallaxPagerAdapter parallaxPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_main);


    parallaxHeader = (RelativeLayout) findViewById(R.id.activity_proxie_header);
    headerHeight = getResources().getDimensionPixelSize(R.dimen.parallax_header_height);
    // set header height for movement of header to desired height.
    mMinHeaderTranslation = -getResources().getDimensionPixelSize(R.dimen.min_header_translation);


    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
    slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);


    viewPager = (ViewPager) findViewById(R.id.view_pager);
    parallaxPagerAdapter = new ParallaxPagerAdapter(getSupportFragmentManager(), this);
    parallaxPagerAdapter.setTabHolderScrollingContent(this);
    viewPager.setAdapter(parallaxPagerAdapter);


    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {

        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = parallaxPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) (parallaxHeader.getHeight() + ViewHelper.getTranslationY(parallaxHeader)), (int) (headerHeight));

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    TAB_VIEW_PADDING_LEFT_RIGHT = 15;
    SlidingTabLayout.TAB_VIEW_PADDING_TOP_BOTTOM = 16;
    SELECTED_INDICATOR_THICKNESS_DIPS = 5;

    slidingTabLayout.setViewPager(viewPager, R.color.colorPrimary, Typeface.createFromAsset(this.getAssets(),
      "Roboto-Regular.ttf"), Typeface.createFromAsset(this.getAssets(), "Roboto-Regular.ttf"));

    SimpleTabColorizer mDefaultTabColorizer = new SimpleTabColorizer();
    mDefaultTabColorizer.setIndicatorColors(Color.parseColor("#ffffff"));
    slidingTabLayout.setCustomTabColorizer(mDefaultTabColorizer);


  }


  private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer {
    private int[] mIndicatorColors;
    private int[] mDividerColors;

    @Override
    public final int getIndicatorColor(int position) {
      return mIndicatorColors[position % mIndicatorColors.length];
    }

    @Override
    public final int getDividerColor(int position) {
      return mDividerColors[position % mDividerColors.length];
    }

    void setIndicatorColors(int... colors) {
      mIndicatorColors = colors;
    }
  }

  @Override
  public void adjustScroll(int scrollHeight, int headerTranslationY) {

  }

  @Override
  public void onScroll(View view, int pagePosition) {

    if (pagePosition == viewPager.getCurrentItem()) {
      if (pagePosition == 0) {
        parallaxHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
      } else if (pagePosition == 1) {
        parallaxHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
      } else if (pagePosition == 2) {
        int scrollY = Utils.getScrollY((AbsListView) view, headerHeight);
        ViewHelper.setTranslationY(parallaxHeader, Math.max(-scrollY, mMinHeaderTranslation));
      }
    }

  }

  @Override
  public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {

  }

  /**
   * Methof for Calculation of header height
   * @return
   */
  @Override
  public int getMinActionBarHeight() {
    int height = 0;
    if (toolbar != null) {
      toolbar.measure(0, 0);
      height = height + toolbar.getMeasuredHeight();
    }
    if (slidingTabLayout != null) {
      slidingTabLayout.measure(0, 0);
      height = height + slidingTabLayout.getMeasuredHeight();
      mMinHeaderTranslation = -headerHeight + slidingTabLayout.getMeasuredHeight();
    }

    return height;
  }
}
