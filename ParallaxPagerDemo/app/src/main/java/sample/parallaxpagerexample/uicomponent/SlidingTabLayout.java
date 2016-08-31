/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.parallaxpagerexample.uicomponent;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sample.parallaxpagerexample.MainActivity;
import sample.parallaxpagerexample.R;

/**
 * To be used with ViewPager to provide a tab indicator component which give
 * constant feedback as to the user's scroll progress.
 * <p/>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout_exam_view_exam_pattern is
 * being used for.
 * <p/>
 * The colors can be customized in two ways. The first and simplest is to
 * provide an array of colors via {@link #setSelectedIndicatorColors(int...)}
 * and {@link #setDividerColors(int...)}. The alternative is via the
 * {@link TabColorizer} interface which provides you complete control over which
 * color is used for any individual position.
 * <p/>
 * The views used as tabs can be customized by calling
 * {@link #setCustomTabView(int, int)}, providing the layout_exam_view_exam_pattern ID of your custom
 * layout_exam_view_exam_pattern.
 */
public class SlidingTabLayout extends HorizontalScrollView {

  public static boolean donotSet = false;

  /**
   * Allows complete control over the colors drawn in the tab layout_exam_view_exam_pattern. Set with
   * {@link #setCustomTabColorizer(TabColorizer)}.
   */
  public interface TabColorizer {

    /**
     * @return return the color of the indicator used when {@code position}
     * is selected.
     */
    int getIndicatorColor(int position);

    /**
     * @return return the color of the divider drawn to the right of
     * {@code position}.
     */
    int getDividerColor(int position);

  }

  private static final int TITLE_OFFSET_DIPS = 24;

  public static int TAB_VIEW_PADDING_TOP_BOTTOM = 14;
  public static int TAB_VIEW_TEXT_SIZE_SP = 14;

  private int mTitleOffset;

  private int mTabViewLayoutId;
  private int mTabViewTextViewId;

  private ViewPager mViewPager;
  public ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

  public final SlidingTabStrip mTabStrip;

  private boolean toSetBg = false;

  private int bgColorUnselect;
  private int bgColorSelect;
  private int txtColorUnselect;
  private int txtColorSelect;


  public SlidingTabLayout(Context context) {
    this(context, null);
  }

  public SlidingTabLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle);

    // Disable the Scroll Bar
    setHorizontalScrollBarEnabled(false);
    // Make sure that the Tab Strips fills this View
    setFillViewport(true);

    mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

    mTabStrip = new SlidingTabStrip(context);
    addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.slider, defStyle, 0);

    donotSet = attributes.getBoolean(R.styleable.slider_donotSet, false);
  }

  /**
   * Set the custom {@link TabColorizer} to be used.
   * <p/>
   * If you only require simple custmisation then you can use
   * {@link #setSelectedIndicatorColors(int...)} and
   * {@link #setDividerColors(int...)} to achieve similar effects.
   */
  public void setCustomTabColorizer(TabColorizer tabColorizer) {
    mTabStrip.setCustomTabColorizer(tabColorizer);
  }

  /**
   * Sets the colors to be used for indicating the selected tab. These colors
   * are treated as a circular array. Providing one color will mean that all
   * tabs are indicated with the same color.
   */
  public void setSelectedIndicatorColors(int... colors) {
    mTabStrip.setSelectedIndicatorColors(colors);
  }

  /**
   * Set the {@link ViewPager.OnPageChangeListener}. When using
   * {@link sample.parallaxpagerexample.SlidingTabLayout} you are required to set any
   * {@link ViewPager.OnPageChangeListener} through this method. This is so
   * that the layout_exam_view_exam_pattern can update it's scroll position correctly.
   *
   * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
   */
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
    mViewPagerPageChangeListener = listener;
  }

  public void isToSetBg(boolean value) {
    toSetBg = value;
  }

  /**
   * Set the custom layout_exam_view_exam_pattern to be inflated for the tab views.
   *
   * @param layoutResId Layout id to be inflated
   * @param textViewId  id of the {@link TextView} in the inflated view
   */
  public void setCustomTabView(int layoutResId, int textViewId) {
    mTabViewLayoutId = layoutResId;
    mTabViewTextViewId = textViewId;
  }

  private boolean isFixed;
  public void setViewPager(ViewPager viewPager, int colorList, Typeface selTypeface, Typeface unselTypefac,boolean isFixed) {
    this.isFixed=isFixed;
    setViewPager(viewPager,colorList,selTypeface,unselTypefac);
  }
  /**
   * Sets the associated view pager. Note that the assumption here is that the
   * pager content (number of tabs and tab titles) does not change after this
   * call has been made.
   */
  public void setViewPager(ViewPager viewPager, int colorList, Typeface selTypeface, Typeface unselTypefac) {
    mTabStrip.removeAllViews();

    mViewPager = viewPager;
    if (viewPager != null) {
      viewPager.setOnPageChangeListener(new InternalViewPagerListener());
      populateTabStrip(colorList, selTypeface, unselTypefac);
    }
  }

  public Typeface setRobotoRegular(Context context) {
    Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf");
    return font;
  }

  /**
   * Create a default view to be used for tabs. This is called if a custom tab
   * view is not set via {@link #setCustomTabView(int, int)}.
   */
  protected TextView createDefaultTabView(Context context, Typeface typeface) {
    TextView textView = new TextView(context);
    textView.setGravity(Gravity.CENTER);

    int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

    if (screenSize >= Configuration.SCREENLAYOUT_SIZE_XLARGE)
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    else
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);

    textView.setTypeface(typeface);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      // If we're running on Honeycomb or newer, then we can use the
      // Theme's
      // selectableItemBackground to ensure that the View has a pressed
      // state
      TypedValue outValue = new TypedValue();
      getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
      textView.setBackgroundResource(outValue.resourceId);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      // If we're running on ICS or newer, enable all-caps to match the
      // Action Bar tab style
      if (!donotSet) {
        textView.setAllCaps(true);
      }
    }


    if(isFixed){
      int padding = (int) (TAB_VIEW_PADDING_TOP_BOTTOM * getResources().getDisplayMetrics().density);
      textView.setPadding(0, padding, 0, padding);
      int width = (int) (getResources().getDisplayMetrics().widthPixels / mViewPager.getAdapter().getCount());
      LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
      params.gravity = Gravity.CENTER;
      textView.setLayoutParams(params);

    }else {
      int paddingLeft = (int) (textView.length() + MainActivity.TAB_VIEW_PADDING_LEFT_RIGHT * getResources().getDisplayMetrics()
        .density);
      int paddingTop = (int) (TAB_VIEW_PADDING_TOP_BOTTOM * getResources().getDisplayMetrics().density);
      textView.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
    }


    return textView;
  }



  private RelativeLayout layout;

  public void setarrowVisiblity(RelativeLayout layout) {
    this.layout = layout;
  }

  protected ImageView createDefaultImageView(Context context) {

    ImageView imageView = new ImageView(context);

    int padding = (int) (MainActivity.TAB_VIEW_PADDING_LEFT_RIGHT * getResources().getDisplayMetrics().density);
    imageView.setPadding(0, padding, 0, padding);

    int width = (int) (getResources().getDisplayMetrics().widthPixels / mViewPager.getAdapter().getCount());
    LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
    params.gravity = Gravity.CENTER;
    imageView.setLayoutParams(params);

    return imageView;
  }

  boolean isDrawIConTab;

  public void setIsDrawIconTab(boolean drawIcon) {
    isDrawIConTab = drawIcon;
  }

  private void populateTabStrip(int colorList, Typeface selTypeface, Typeface unselTypeface) {
    final PagerAdapter adapter = mViewPager.getAdapter();
    final OnClickListener tabClickListener = new TabClickListener();

    for (int i = 0; i < adapter.getCount(); i++) {
      View tabView = null;
      TextView tabTitleView = null;

      if (isDrawIConTab) {
        ImageView tabIconView = null;

        Log.e("sliding_tab", "sliding tab");

        if (tabView == null) {
          tabView = createDefaultImageView(getContext());
        }

        if (tabIconView == null && ImageView.class.isInstance(tabView)) {
          tabIconView = (ImageView) tabView;
        }

//        tabIconView.setImageDrawable(getResources().getDrawable(((HomePagerAdapter) adapter).getDrawableId(i)));
        if (mViewPager.getCurrentItem() == i) {
          tabIconView.setSelected(true);
        }
        //tabTitleView.setText(adapter.getPageTitle(i));
        tabView.setOnClickListener(tabClickListener);

        mTabStrip.addView(tabView);
      }else {

        if (mTabViewLayoutId != 0) {
          // If there is a custom tab view layout_exam_view_exam_pattern id set, try and inflate
          // it
          tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip, false);
          tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
        }

        // set typeface for all tabs except college view
        if (tabView == null) {
          tabView = createDefaultTabView(getContext(), selTypeface);
        }

        if (tabTitleView == null && TextView.class.isInstance(tabView)) {
          tabTitleView = (TextView) tabView;
        }

        if (!donotSet) {
          tabTitleView.setText(adapter.getPageTitle(i).toString().toUpperCase());
        } else {
          tabTitleView.setText(adapter.getPageTitle(i).toString());
        }

        tabView.setOnClickListener(tabClickListener);

        mTabStrip.addView(tabView);

        if (i == mViewPager.getCurrentItem()) {
          tabView.setSelected(true);
        }

        if (toSetBg) {
          if (tabView.isSelected()) {
            setCustomColor(true, (TextView) tabView);
            ((TextView) tabView).setTypeface(selTypeface);
          } else {
            setCustomColor(false, (TextView) tabView);
            ((TextView) tabView).setTypeface(unselTypeface);
          }
        }
        // set color selector
        else
          tabTitleView.setTextColor(getResources().getColorStateList(colorList));
      }
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    if (mViewPager != null) {
      scrollToTab(mViewPager.getCurrentItem(), 0);
    }
  }

  public void setToSetColor(int bgColorSelect, int txtColorSelect, int bgColorUnselect, int txtColorUnselect) {
    this.bgColorSelect = bgColorSelect;
    this.bgColorUnselect = bgColorUnselect;
    this.txtColorSelect = txtColorSelect;
    this.txtColorUnselect = txtColorUnselect;
  }

  public void scrollToTab(int tabIndex, int positionOffset) {

    if (layout != null) {
      if (tabIndex == 0) {
        layout.setVisibility(View.VISIBLE);
      } else
        layout.setVisibility(View.INVISIBLE);
    }
    final int tabStripChildCount = mTabStrip.getChildCount();
    if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
      return;
    }

    View selectedChild = mTabStrip.getChildAt(tabIndex);
    if (selectedChild != null) {
      int targetScrollX = selectedChild.getLeft() + positionOffset;

      if (tabIndex > 0 || positionOffset > 0) {
        // If we're not at the first child and are mid-scroll, make sure
        // we obey the offset
        targetScrollX -= mTitleOffset;
      }

      scrollTo(targetScrollX, 0);
    }

  }

  private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
    private int mScrollState;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      int tabStripChildCount = mTabStrip.getChildCount();
      if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
        return;
      }

      mTabStrip.onViewPagerPageChanged(position, positionOffset);

      View selectedTitle = mTabStrip.getChildAt(position);

      int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
      scrollToTab(position, extraOffset);

      if (mViewPagerPageChangeListener != null) {
        mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

      mScrollState = state;

      if (mViewPagerPageChangeListener != null) {
        mViewPagerPageChangeListener.onPageScrollStateChanged(state);
      }
    }

    @Override
    public void onPageSelected(int position) {

      if (isDrawIConTab) {
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
          mTabStrip.getChildAt(i).setSelected(false);
        }
        mTabStrip.getChildAt(position).setSelected(true);

        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
          mTabStrip.onViewPagerPageChanged(position, 0f);
          scrollToTab(position, 0);
        }

        if (mViewPagerPageChangeListener != null) {
          mViewPagerPageChangeListener.onPageSelected(position);
        }


      } else {

        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
          mTabStrip.onViewPagerPageChanged(position, 0f);
          scrollToTab(position, 0);
        }

        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
          mTabStrip.getChildAt(i).setSelected(position == i);
          TextView view = (TextView) mTabStrip.getChildAt(i);
          if (toSetBg) {
            setCustomColor(position == i, view);
          }
        }

        if (mViewPagerPageChangeListener != null) {
          Log.e("ParellexEffect", "slidingtablayout onpageselected");
          mViewPagerPageChangeListener.onPageSelected(position);
        }
      }
    }


  }

  private void setCustomColor(boolean selected, TextView view) {
    if (selected) {
      view.setTextColor(txtColorSelect);
      view.setBackgroundColor(bgColorSelect);
    } else {
      view.setTextColor(txtColorUnselect);
      view.setBackgroundColor(bgColorUnselect);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        view.setBackground(getResources().getDrawable(R.drawable.tab_bg));
      } else {
        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_bg));
      }
    }
  }

  private class TabClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      for (int i = 0; i < mTabStrip.getChildCount(); i++) {
        if (v == mTabStrip.getChildAt(i)) {

          if (mViewPager != null)
            mViewPager.setCurrentItem(i,true);

          if (layout != null) {
            if (i == 0) {
              layout.setVisibility(View.VISIBLE);
            } else if (i == 1)
              layout.setVisibility(View.INVISIBLE);
          }
          return;
        }

      }
    }
  }
}
