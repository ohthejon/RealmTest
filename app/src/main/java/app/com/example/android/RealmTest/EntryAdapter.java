package app.com.example.android.RealmTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ohthe on 4/26/2016.
 */
public class EntryAdapter extends RealmBasedRecyclerViewAdapter<Entry, EntryAdapter.ViewHolder> {


    public class ViewHolder extends RealmViewHolder {
        public TextView entryTextView;
        public ViewHolder(FrameLayout container){
            super(container);
            this.entryTextView = (TextView) container.findViewById(R.id.text_view);
        }
    }

    public EntryAdapter(Context context, RealmResults<Entry> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        return vh;
    }

    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Entry entry = realmResults.get(position);
        viewHolder.entryTextView.setText(entry.getEntryDate());

    }

    /*public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Entry entry = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.data_entry, parent, false);
            viewHolder.painMorn = (TextView) convertView.findViewById(R.id.tvpainMorn);
            viewHolder.painMid = (TextView) convertView.findViewById(R.id.tvpainMid);
            viewHolder.painNight = (TextView) convertView.findViewById(R.id.tvpainNight);
            viewHolder.sleepTime = (TextView) convertView.findViewById(R.id.tvsleepTime);
            viewHolder.sleepLength = (TextView) convertView.findViewById(R.id.tvsleepLength);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.painMorn.setText(""+entry.painMorn);
        viewHolder.painMid.setText(""+entry.painMid);
        viewHolder.painNight.setText(""+entry.painNight);
        viewHolder.sleepTime.setText(""+entry.sleepTime);
        viewHolder.sleepLength.setText(""+entry.sleepLength);

        // Return the completed view to render on screen
        return convertView;
    }*/


}
