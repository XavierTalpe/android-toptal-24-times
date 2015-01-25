package be.xvrt.times.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.dialog.DialogDismissOnClickListener;
import be.xvrt.times.model.Clock;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class NewClockDialog extends DialogFragment {

    private ClockCreationListener mListener;

    public NewClockDialog() {
    }

    public void setResultListener(ClockCreationListener listener) {
        mListener = listener;
    }

    private void notifyListeners(Clock newClock) {
        if (mListener != null) {
            mListener.onClockCreatedListener(newClock);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();

        final View dialogView = createDialogView(activity);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setNegativeButton(R.string.cancelCreate, new DialogDismissOnClickListener());
        builder.setPositiveButton(R.string.createClock, new DialogDismissOnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);

                Clock clock = extractClock(dialogView);
                notifyListeners(clock);
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mListener = null;
    }

    private View createDialogView(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.new_clock_dialog, null);
        dialogView.setTag(new ViewLookupTable(dialogView));

        populateView(dialogView);

        return dialogView;
    }

    private void populateView(View dialogView) {
        ViewLookupTable lookupTable = (ViewLookupTable) dialogView.getTag();

        BaseAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.timezones, android.R.layout.simple_spinner_item);
        lookupTable.timezoneLst.setAdapter(adapter);
    }

    private Clock extractClock(View dialogView) {
        ViewLookupTable lookupTable = (ViewLookupTable) dialogView.getTag();
        TextView cityTxt = lookupTable.cityTxt;
        Spinner timezoneLst = lookupTable.timezoneLst;

        Clock clock = new Clock();
        clock.setCity(cityTxt.getText().toString());
        clock.setTimezone(timezoneLst.getSelectedItem().toString());

        return clock;
    }

    public interface ClockCreationListener {

        public void onClockCreatedListener(Clock newClock);

    }

    static final class ViewLookupTable {

        @InjectView(R.id.timezoneLst)
        Spinner timezoneLst;

        @InjectView(R.id.cityTxt)
        TextView cityTxt;

        public ViewLookupTable(View view) {
            ButterKnife.inject(this, view);
        }

    }

}
