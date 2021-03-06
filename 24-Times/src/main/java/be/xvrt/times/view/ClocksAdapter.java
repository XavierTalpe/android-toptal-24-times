package be.xvrt.times.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.model.Clock;
import be.xvrt.times.model.ClocksStore;
import be.xvrt.times.model.Timezone;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ClocksAdapter extends BaseAdapter implements ClocksStore.ClocksStoreListener {

    private final ClocksStore store;
    private final LayoutInflater inflater;

    private List<Clock> clocks;
    private String cityFilter;

    public ClocksAdapter(Context context, ClocksStore store) {
        this.store = store;
        this.store.addListener(this);

        this.inflater = LayoutInflater.from(context);

        this.clocks = new ArrayList<Clock>(0);
        this.cityFilter = null;
    }

    public void setFilter(String cityFilter) {
        this.cityFilter = cityFilter;

        invalidateDataSet();
    }

    @Override
    public int getCount() {
        return clocks.size();
    }

    @Override
    public Object getItem(int position) {
        return clocks.get(position);
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

        populateView(position, inputView);

        return inputView;
    }

    private void populateView(int position, View inputView) {
        Clock clock = (Clock) getItem(position);
        String city = clock.getCity();
        String timezoneId = clock.getTimezone();
        Timezone timezone = Timezone.valueOf(timezoneId);

        LookupTable tag = (LookupTable) inputView.getTag();
        tag.timezoneTxt.setText(timezone.name());
        tag.gmtTime.setText(timezone.asGMT());
        tag.cityTxt.setText(city);
        tag.timeTxt.setTimeZone(timezone.name());

        GradientDrawable timezoneBackground = (GradientDrawable) tag.timezoneTxt.getBackground();
        timezoneBackground.setColor(timezone.getColor());

        GradientDrawable gmtBackground = (GradientDrawable) tag.gmtTime.getBackground();
        gmtBackground.setColor(Color.rgb(102, 153, 0));
    }

    @Override
    public void onClocksStoreUpdated() {
        invalidateDataSet();
    }

    private void invalidateDataSet() {
        if (cityFilter == null) {
            clocks = store.queryAll();
        } else {
            clocks = store.query(cityFilter);
        }

        notifyDataSetChanged();
    }

    static final class LookupTable {

        @InjectView(R.id.timezoneTxt)
        TextView timezoneTxt;

        @InjectView(R.id.gmtDifferenceTxt)
        TextView gmtTime;

        @InjectView(R.id.cityTxt)
        TextView cityTxt;

        @InjectView(R.id.timeClock)
        TextClock timeTxt;

        public LookupTable(View view) {
            ButterKnife.inject(this, view);
        }

    }

}
