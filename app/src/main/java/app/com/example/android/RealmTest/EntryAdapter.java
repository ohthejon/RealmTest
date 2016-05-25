package app.com.example.android.RealmTest;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ohthe on 4/26/2016.
 */
public class EntryAdapter extends RealmBasedRecyclerViewAdapter<Entry, EntryAdapter.ViewHolder> {

    public TextView tvEntryDate, tvAvgDayPain;
    private RealmResults<Entry> adapterData;
    protected Context context;
    private final RealmChangeListener listener;


    public class ViewHolder extends RealmViewHolder {
        public ViewHolder(FrameLayout container){
            super(container);
            tvEntryDate = (TextView) container.findViewById(R.id.tv_entry_date);
            tvAvgDayPain = (TextView) container.findViewById(R.id.tv_avg_pain);
        }
    }

    public EntryAdapter(Context context, RealmResults<Entry> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.context = context;
        this.adapterData = realmResults;
        this.listener = new RealmChangeListener<RealmResults<Entry>>() {
            @Override
            public void onChange(RealmResults<Entry> results) {
                notifyDataSetChanged();
            }
        };
        if (realmResults != null) {
            addListener(realmResults);
        }
    }

    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        return vh;
    }

    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        Entry entry = realmResults.get(position);
        tvEntryDate.setText(entry.getEntryDate());
        tvAvgDayPain.setText(""+entry.getAveragePain());
    }

    public long getItemId(int position) {
        // TODO: find better solution once we have unique IDs
        return position;
    }

    @Override
    public int getItemCount() {
        if (adapterData == null) {
            return 0;
        }
        return adapterData.size();
    }

    public Entry getItem(int position) {
        if (adapterData == null) {
            return null;
        }
        return adapterData.get(position);
    }
    private void addListener(RealmResults<Entry> realmResults) {
        if (realmResults instanceof RealmResults) {
            RealmResults entries = (RealmResults) realmResults;
            entries.addChangeListener(listener);
        }
    }

    private void removeListener(RealmResults<Entry> realmResults) {
        if (realmResults instanceof RealmResults) {
            RealmResults entries = (RealmResults) realmResults;
            entries.removeChangeListener(listener);
        }
    }

    public void updateData(RealmResults<Entry> realmResults) {
        if (listener != null) {
            if (adapterData != null) {
                removeListener(realmResults);
            }
            if (realmResults != null) {
                addListener(realmResults);
            }
        }

        this.adapterData = realmResults;
        notifyDataSetChanged();
    }
}
