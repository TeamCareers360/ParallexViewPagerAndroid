package com.careers.parallaxeffectlib;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import com.careers.parallaxeffectlib.listeners.Constant;

public class BaseActivity extends AppCompatActivity {

  public int headerHeight;
  public int mMinHeaderTranslation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * @return the action bar height.
   * <p>Method will evaluate the min height of action bar that will no translate after parallax effect</p>
   */
  public int getMinActionBarHeight() {
    TypedValue mTypedValue = new TypedValue();
    int mActionBarHeight = 0;

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
      getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
    } else {
      getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
    }
    mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());

    return mActionBarHeight;
  }

}
