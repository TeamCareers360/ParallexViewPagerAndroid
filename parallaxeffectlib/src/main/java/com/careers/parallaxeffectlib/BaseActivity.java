package com.careers.parallaxeffectlib;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import com.careers.parallaxeffectlib.listeners.Constant;

public class BaseActivity extends AppCompatActivity {

  public int mMinHeaderTranslation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
  }

  public int getMinParallexHeaderHeight() {
    TypedValue mTypedValue = new TypedValue();
    int mActionBarHeight = 0;

    if (mActionBarHeight != 0) {
      return mActionBarHeight;
    }

    if (mActionBarHeight == 0) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
        getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
      } else {
        getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
      }
      mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
    }

    Log.e(Constant.LOG_TAG, "mActionBarHEight :- " + mActionBarHeight);
    return mActionBarHeight;
  }

}
