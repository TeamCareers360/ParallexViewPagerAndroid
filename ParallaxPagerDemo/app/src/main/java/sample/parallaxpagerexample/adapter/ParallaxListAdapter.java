package sample.parallaxpagerexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sample.parallaxpagerexample.R;

/**
 * Created by ManuJaggi on 31-08-2016.
 */
public class ParallaxListAdapter extends BaseAdapter {
  private Context context;
  private ArrayList<String> alNames;
  private LayoutInflater inflater;

  public ParallaxListAdapter(Context context, ArrayList<String> alNames) {
    this.context = context;
    this.alNames = alNames;
  }

  @Override
  public int getCount() {
    return alNames.size();
  }

  @Override
  public Object getItem(int position) {
    return alNames.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder = null;
    if (inflater == null) {
      inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.parallax_list_item, null);
      holder = new ViewHolder();
      holder.txtTitle = (TextView) convertView.findViewById(R.id.tv_item);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();

    }
    holder.txtTitle.setText(alNames.get(position));

    return convertView;
  }


  private class ViewHolder {

    TextView txtTitle;

  }

}
