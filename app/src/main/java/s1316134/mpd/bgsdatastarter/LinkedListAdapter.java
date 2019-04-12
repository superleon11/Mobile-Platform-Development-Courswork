
//
// Name                 Jordan O'Donnell
// Student ID           s1316134
// Programme of Study   Computing
//


package s1316134.mpd.bgsdatastarter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListAdapter extends ArrayAdapter<Earthquake> {

    private static class ViewHolder {
        private TextView textView;
    }

    public LinkedListAdapter(Context context, int textViewResourceId, LinkedList<Earthquake> values) {
        super(context, textViewResourceId, values);

    }







    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.activity_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Earthquake item = getItem(position);
        if (item!= null) {

            System.out.println("Random" + item.getTitle());
            viewHolder.textView.setText(item.toString());
            //viewHolder.textView.setBackgroundColor(Color.RED);
            if(item.getColour()!=null){
                String colour = item.getColour();

                if(colour.equals("green")){
                    viewHolder.textView.setBackgroundColor(Color.GREEN);
                }
                if(colour.equals("yellow")){
                    viewHolder.textView.setBackgroundColor(Color.YELLOW);
                }
                if(colour.equals("red")){
                    viewHolder.textView.setBackgroundColor(Color.RED);
                }
            }

        }

        return convertView;
    }


}
