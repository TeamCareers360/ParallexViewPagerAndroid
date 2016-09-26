package sample.parallaxpagerexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.careers.parallaxeffectlib.fragments.ParallaxListViewFragment;

import java.util.ArrayList;

import sample.parallaxpagerexample.R;
import sample.parallaxpagerexample.adapter.ParallaxListAdapter;

/**
 * Created by ManuJaggi on 31-08-2016.
 */
public class ThirdFragment extends ParallaxListViewFragment implements View.OnClickListener {


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private View rootView;
  private ListView listParallax;
  private ArrayList<String> alNames;
  private Button addView;
  private Button removeView;
  private ParallaxListAdapter parallaxListAdapter;
  private View headerView;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.third_fragment, container, false);
    }

    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    listParallax = (ListView) view.findViewById(R.id.notifying_list_view);
    alNames = new ArrayList<String>();
    addNames();
    parallaxListAdapter = new ParallaxListAdapter(view.getContext(), alNames);
    if (headerView == null) {
      headerView = ((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R
        .layout.list_header, null, false);
      listParallax.addHeaderView(headerView);
    }
    listParallax.setAdapter(parallaxListAdapter);
    addView = (Button) view.findViewById(R.id.add_view);
    removeView = (Button) view.findViewById(R.id.remove_view);
    addView.setOnClickListener(this);
    removeView.setOnClickListener(this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  public void addNames() {
    alNames.add("    Apple   ");
    alNames.add("    Banana  ");
    alNames.add("    Orange  ");

    alNames.add("    Apple   ");
    alNames.add("    Banana  ");
    alNames.add("    Orange  ");

    alNames.add("    Apple   ");
    alNames.add("    Banana  ");
    alNames.add("    Orange  ");

    alNames.add("    Apple   ");
    alNames.add("    Banana  ");
    alNames.add("    Orange  ");

    alNames.add("    Apple   ");
    alNames.add("    Banana  ");
    alNames.add("    Orange  ");
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add_view:
        alNames.add("    Orange  ");
        parallaxListAdapter.notifyDataSetChanged();
        evaluateHeightAfterLoading();

        break;
      case R.id.remove_view:
        if (alNames.size() > 1) {
          alNames.remove(alNames.size() - 1);

          parallaxListAdapter.notifyDataSetChanged();
          evaluateHeightAfterLoading();
        }
        break;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e("Header count",""+listParallax.getHeaderViewsCount());
  }
}
