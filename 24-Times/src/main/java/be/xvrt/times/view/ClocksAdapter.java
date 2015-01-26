package be.xvrt.times.view;

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

final class ClocksAdapter extends BaseAdapter implements ClocksStore.ClocksStoreListener {

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
        tag.gmtDifferenceTxt.setText(timezone.name());
        tag.cityTxt.setText(city);
        tag.timeTxt.setTimeZone(timezone.name());

        GradientDrawable timezoneBackground = (GradientDrawable) tag.timezoneTxt.getBackground();
        timezoneBackground.setColor(timezone.getColor());

        GradientDrawable gmtBackground = (GradientDrawable) tag.gmtDifferenceTxt.getBackground();
        gmtBackground.setColor(Color.rgb(102, 153, 0));
    }

    @Override
    public void onClocksStoreUpdated() {
        notifyDataSetChanged();
    }

    static final class LookupTable {

        @InjectView(R.id.timezoneTxt)
        TextView timezoneTxt;

        @InjectView(R.id.gmtDifferenceTxt)
        TextView gmtDifferenceTxt;

        @InjectView(R.id.cityTxt)
        TextView cityTxt;

        @InjectView(R.id.timeClock)
        TextClock timeTxt;

        public LookupTable(View view) {
            ButterKnife.inject(this, view);
        }

    }

}
