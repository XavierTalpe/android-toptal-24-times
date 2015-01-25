package be.xvrt.times.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ClocksAdapter extends BaseAdapter implements ClocksStore.ClocksStoreListener {

    private final ClocksStore store;
    private final LayoutInflater inflater;

    public ClocksAdapter(Context context, ClocksStore store) {
        this.store = store;
        this.store.addListener(this);

        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return store.getCount();
    }

    @Override
    public Object getItem(int position) {
        return store.getClock(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View inputView, ViewGroup parent) {
        if (inputView == null) {
            inputView = inflater.inflate(R.layout.clock_view, parent, false);
            inputView.setTag(new LookupTable(inputView));
        }

        Clock clock = (Clock) getItem(position);

        LookupTable tag = (LookupTable) inputView.getTag();
        tag.timezoneTxt.setText(clock.getTimezone());
        tag.cityTxt.setText(clock.getCity());

        return inputView;
    }

    @Override
    public void onClocksStoreUpdated(List<Clock> clocks) {
        notifyDataSetChanged();
    }

    static final class LookupTable {

        @InjectView(R.id.timezoneTxt)
        TextView timezoneTxt;

        @InjectView(R.id.cityTxt)
        TextView cityTxt;

        @InjectView(R.id.timeTxt)
        TextView timeTxt;

        public LookupTable(View view) {
            ButterKnife.inject(this, view);
        }

    }

}