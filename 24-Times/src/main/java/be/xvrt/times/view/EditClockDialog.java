package be.xvrt.times.view;


import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.dialog.DialogDismissOnClickListener;
import be.xvrt.times.model.Clock;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class EditClockDialog extends DialogFragment {

    private Clock editObject;

    private ClockEditListener listener;

    public EditClockDialog() {
    }

    public void setEditObject(Clock editObject) {
        this.editObject = editObject;
    }

    public void setResultListener(ClockEditListener listener) {
        this.listener = listener;
    }

    private void notifyListeners(Clock clock) {
        if (listener != null) {
            if (editObject == null) {
                listener.onClockCreatedListener(clock);
            } else {
                listener.onClockUpdatedListener(clock);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();

        final View dialogView = createDialogView(activity);

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setTitle(editObject == null ? R.string.newClockTitle : R.string.editClockTitle);
        builder.setNegativeButton(R.string.cancelEdit, new DialogDismissOnClickListener());
        builder.setPositiveButton(R.string.editClockOK, new DialogDismissOnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);

                handleEdit(dialogView);
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listener = null;
        editObject = null;
    }

    private View createDialogView(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.edit_clock_dialog, null);
        dialogView.setTag(new ViewLookupTable(dialogView));

        populateView(activity, dialogView, editObject);

        return dialogView;
    }

    private void populateView(Context context, View dialogView, Clock editObject) {
        TimezoneAdapter timezoneAdapter = new TimezoneAdapter(context);

        ViewLookupTable lookupTable = (ViewLookupTable) dialogView.getTag();
        lookupTable.timezoneLst.setAdapter(timezoneAdapter);

        if (editObject != null) {
            int itemId = timezoneAdapter.getItemId(editObject.getTimezone());

            lookupTable.timezoneLst.setSelection(itemId);
            lookupTable.cityTxt.setText(editObject.getCity());
        }
    }

    private void handleEdit(View dialogView) {
        Clock clock = extractClock(dialogView);

        notifyListeners(clock);
    }

    private Clock extractClock(View dialogView) {
        ViewLookupTable lookupTable = (ViewLookupTable) dialogView.getTag();
        TextView cityTxt = lookupTable.cityTxt;
        Spinner timezoneLst = lookupTable.timezoneLst;

        Clock clock = editObject == null ? new Clock() : editObject;
        clock.setUser(ParseUser.getCurrentUser());
        clock.setCity(cityTxt.getText().toString());
        clock.setTimezone(timezoneLst.getSelectedItem().toString());

        return clock;
    }

    public interface ClockEditListener {

        public void onClockCreatedListener(Clock newClock);

        public void onClockUpdatedListener(Clock updatedClock);

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
