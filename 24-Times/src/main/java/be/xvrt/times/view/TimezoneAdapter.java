package be.xvrt.times.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import be.xvrt.times.model.Timezone;

final class TimezoneAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Timezone[] timezones;
    private final LayoutInflater inflater;

    public TimezoneAdapter(Context context) {
        timezones = Timezone.values();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timezones.length;
    }

    @Override
    public Object getItem(int position) {
        return timezones[position];
    }

    public int getItemId(String item) {
        int itemId = 0;

        int nbTimezones = timezones.length;
        for (int i = 0; i < nbTimezones; i++) {
            Timezone timezone = timezones[i];

            if (timezone.name().equals(item)) {
                itemId = i;
                break;
            }
        }

        return itemId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View inputView, ViewGroup parent) {
        if (inputView == null) {
            inputView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Timezone timezone = timezones[position];
        TextView textView = (TextView) inputView;
        textView.setText(timezone.getLongName());

        return inputView;
    }

}
