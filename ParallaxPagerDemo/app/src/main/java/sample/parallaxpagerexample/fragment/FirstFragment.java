package sample.parallaxpagerexample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sample.parallaxpagerexample.R;

/**
 * Created by ManuJaggi on 31-08-2016.
 */
public class FirstFragment extends Fragment {

  private View rootView;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.first_fragment, container, false);
    }

    return rootView;
  }
}
