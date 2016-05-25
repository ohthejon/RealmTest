package app.com.example.android.RealmTest;

import android.content.Context;
import android.support.annotation.UiThread;
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

    public TextView tvEntryDate, tvAvgDayPain;
    private final MainActivity activity;
    public RealmResults<Entry> realmResults;

    public class ViewHolder extends RecyclerView.ViewHolderr {
        public ViewHolder(FrameLayout container){
            super(container);
            tvEntryDate = (TextView) container.findViewById(R.id.tv_entry_date);
            tvAvgDayPain = (TextView) container.findViewById(R.id.tv_avg_pain);
        }
    }

    public EntryAdapter(MainActivity activity, RealmResults<Entry> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(activity, realmResults, automaticUpdate, animateResults);
        this.activity = activity;
    }

    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        return vh;
    }

    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
//        final Entry entry = realmResults.get(position);
//        tvEntryDate.setText(entry.getEntryDate());
//        tvAvgDayPain.setText(""+entry.getAveragePain());
        Entry entry = realmResults.get(position);
        viewHolder.
    }

}
