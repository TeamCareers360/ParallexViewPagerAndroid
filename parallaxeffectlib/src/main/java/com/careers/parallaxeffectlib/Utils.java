package com.careers.parallaxeffectlib;

import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;

/**
 * Created by Abha Dhiman on 23-08-2016.
 */
public class Utils {

  public static int getActionBarHeight(BaseActivity activity, Resources.Theme theme) {
    int mActionBarHeight = activity.getMinParallexHeaderHeight();
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

}
