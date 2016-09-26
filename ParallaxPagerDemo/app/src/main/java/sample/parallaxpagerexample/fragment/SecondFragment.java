package sample.parallaxpagerexample.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careers.parallaxeffectlib.fragments.ParallaxScrollViewFragment;
import com.careers.parallaxeffectlib.listeners.ScrollTabHolder;

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
    private int index;

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
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout parent = new LinearLayout(activity);
        parent.setLayoutParams(lp);

        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2,
                activity.getResources().getDisplayMetrics());
        parent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        LinearLayout child = new LinearLayout(activity);
        child.setBackgroundColor(Color.parseColor("#000000"));
        child.setLayoutParams(lp1);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                20,
                activity.getResources().getDisplayMetrics());
        lp1.setMargins(0, px, 0, px);

        TextView tv = new TextView(activity);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setPadding(20, 0, 0, 0);
        if (index == 0) {
            tv.setText("  Apple  ");
        } else if (index == 1) {
            tv.setText("  Banana  ");
        } else if (index == 2) {
            tv.setText("  Orange  ");
            index = -1;
        }
        parent.addView(tv);
        parent.addView(child);

        dynamicContent.addView(parent);
        index++;
    }

    public void removeViews() {
        if (dynamicContent.getChildCount() > 1) {
            dynamicContent.removeViewAt(0);
        }

    }
}
