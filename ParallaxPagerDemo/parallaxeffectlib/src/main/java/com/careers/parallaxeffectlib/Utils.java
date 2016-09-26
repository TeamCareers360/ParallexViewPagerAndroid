package com.careers.parallaxeffectlib;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Abha Dhiman on 23-08-2016.
 */
public class Utils {

  /**
   * @param activity : Reference of activity should be instance of BaseActivity
   * @param theme : Theme reference used in app.
   * @return : min action bar height.
   * <p>Method will be used to get the action bar height</p>
   */
  public static int getActionBarHeight(BaseActivity activity, Resources.Theme theme) {
    int mActionBarHeight = activity.getMinActionBarHeight();
    if (mActionBarHeight == 0) {
      TypedValue mTypedValue = new TypedValue();

      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
        theme.resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
      } else {
        theme.resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
      }
      mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, activity.getResources().getDisplayMetrics());
    }
    return mActionBarHeight;
  }

  /**
   * @param view : A AbsListView reference.
   * @param headerHeight : height of parallax header.
   * @return : scroll value.
   * <p>Method used to evaluate the scrollY value for ListView</p>
   */
  public static int getScrollY(AbsListView view,int headerHeight) {

    View c = view.getChildAt(0);

    if (c == null) {
      return 0;
    }

    int firstVisiblePosition = view.getFirstVisiblePosition();
    int top = c.getTop();

    int headerHeight1 = 0;
    if (firstVisiblePosition >= 1) {
      headerHeight1 = headerHeight;
    }

    Log.e("PARAALAX"," getScrollY : "+(-top + firstVisiblePosition * c.getHeight() + headerHeight1));
    return -top + firstVisiblePosition * c.getHeight() + headerHeight1;
  }

}
