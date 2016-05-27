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

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by ohthe on 4/26/2016.
 */
public class EntryAdapter extends RealmRecyclerViewAdapter<Entry, EntryAdapter.ViewHolder> {

    protected LayoutInflater inflater;
    public TextView tvEntryDate, tvAvgDayPain;
    private OrderedRealmCollection<Entry> adapterData;
    //protected Context context;
    private final RealmChangeListener listener;
    private final MainActivity activity;


    public EntryAdapter(MainActivity activity, OrderedRealmCollection<Entry> data) {
        super(activity, data, true);
        this.activity = activity;
        this.adapterData = data;
        this.inflater = LayoutInflater.from(activity);
        this.listener = new RealmChangeListener<RealmResults<Entry>>() {
            @Override
            public void onChange(RealmResults<Entry> results) {
                notifyDataSetChanged();
            }
        };
        if (data != null) {
            addListener(data);
        }
    }

    public class ViewHolder extends RealmViewHolder {
        public ViewHolder(FrameLayout container){
            super(container);
            tvEntryDate = (TextView) container.findViewById(R.id.tv_entry_date);
            tvAvgDayPain = (TextView) container.findViewById(R.id.tv_avg_pain);
        }
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
    private void addListener(OrderedRealmCollection<Entry> data) {
        if (data instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) data;
            realmResults.addChangeListener(listener);
        }
    }

    private void removeListener(OrderedRealmCollection<Entry> data) {
        if (data instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) data;
            realmResults.removeChangeListener(listener);
        }
    }

    public void updateData(OrderedRealmCollection<Entry> data) {
        if (listener != null) {
            if (adapterData != null) {
                removeListener(adapterData);
            }
            if (data != null) {
                addListener(data);
            }
        }

        this.adapterData = data;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Entry entry = getData().get(position);
        tvEntryDate.setText(entry.getEntryDate());
        tvAvgDayPain.setText(""+entry.getAveragePain());
    }

}
