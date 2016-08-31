package sample.parallaxpagerexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careers.parallaxeffectlib.fragments.ParallaxScrollViewFragment;

import sample.parallaxpagerexample.R;

/**
 * Created by ManuJaggi on 31-08-2016.
 */
public class SecondFragment extends ParallaxScrollViewFragment implements View.OnClickListener {

  private Button addView;
  private Button removeView;
  private View rootView;
  private LinearLayout dynamicContent;
  private Context activity;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.second_fragment, container, false);

    }

    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    addView = (Button) view.findViewById(R.id.add_view);
    removeView = (Button) view.findViewById(R.id.remove_view);
    dynamicContent = (LinearLayout) view.findViewById(R.id.dynamic_content);
    addView.setOnClickListener(this);
    removeView.setOnClickListener(this);

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();


  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add_view:
        addViews();
        break;
      case R.id.remove_view:
        removeViews();
        break;
    }
  }

  public void addViews() {
    TextView tv = new TextView(activity);
    tv.setText("  Parallax Example Text  ");
    tv.setTextSize(30);
    tv.setPadding(10, 10, 10, 10);
    dynamicContent.addView(tv);
  }

  public void removeViews() {
    if (dynamicContent.getChildCount() > 1) {
      dynamicContent.removeViewAt(0);
    }

  }
}
