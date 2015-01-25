package be.xvrt.times.view;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;

public final class ClocksAdapter extends BaseAdapter implements ClocksStore.ClocksStoreListener {

    private final ClocksStore store;

    public ClocksAdapter(ClocksStore store) {
        this.store = store;
        this.store.addListener(this);
    }

    @Override
    public int getCount() {
        return store.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        return null;
    }

    @Override
    public void onClocksStoreUpdated(List<Clock> clocks) {
        notifyDataSetChanged();
    }

}
