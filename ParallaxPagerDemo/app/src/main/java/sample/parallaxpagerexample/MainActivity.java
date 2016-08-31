package sample.parallaxpagerexample;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;

import com.careers.parallaxeffectlib.BaseActivity;

import sample.parallaxpagerexample.adapter.ParallaxPagerAdapter;
import sample.parallaxpagerexample.uicomponent.SlidingTabLayout;

public class MainActivity extends BaseActivity {

  public static int TAB_VIEW_PADDING_LEFT_RIGHT;
  public static int SELECTED_INDICATOR_THICKNESS_DIPS;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
    viewPager = (ViewPager) findViewById(R.id.view_pager);
    ParallaxPagerAdapter parallaxPagerAdapter = new ParallaxPagerAdapter(getSupportFragmentManager(), this);
    viewPager.setAdapter(parallaxPagerAdapter);

    TAB_VIEW_PADDING_LEFT_RIGHT = 15;
    SlidingTabLayout.TAB_VIEW_PADDING_TOP_BOTTOM = 16;
    SELECTED_INDICATOR_THICKNESS_DIPS = 5;

  }
}
