package com.careers.parallaxeffectlib.listeners;

import android.view.View;
import android.widget.ScrollView;

public interface ScrollTabHolder {
  void adjustScroll(int scrollHeight, int headerTranslationY);

  void onScroll(View view, int pagePosition);

  void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition);
}
