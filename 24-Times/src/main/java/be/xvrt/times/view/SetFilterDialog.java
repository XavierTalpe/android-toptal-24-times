package be.xvrt.times.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import be.xvrt.times.R;
import be.xvrt.times.dialog.DialogDismissOnClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

public final class SetFilterDialog extends DialogFragment {

    private FilterEditListener listener;

    public SetFilterDialog() {
    }

    public void setResultListener(FilterEditListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();

        final View dialogView = createDialogView(activity);

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setTitle(R.string.setFilterTitle);
        builder.setNegativeButton(R.string.cancelEdit, new DialogDismissOnClickListener());
        builder.setPositiveButton(R.string.editClockOK, new DialogDismissOnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);

                handleSuccess(dialogView);
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listener = null;
    }

    private View createDialogView(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.set_filter_dialog, null);
        dialogView.setTag(new ViewLookupTable(dialogView));

        return dialogView;
    }

    private void handleSuccess(View dialogView) {
        ViewLookupTable lookupTable = (ViewLookupTable) dialogView.getTag();
        TextView cityFilterTxt = lookupTable.cityFilterTxt;

        String cityFilter = cityFilterTxt.getText().toString();
        if (cityFilter.length() == 0 ) {
            cityFilter = null;
        }

        notifyListeners(cityFilter);
    }

    private void notifyListeners(String clock) {
        if (listener != null) {
            listener.onFilterUpdated(clock);
        }
    }

    public interface FilterEditListener {

        public void onFilterUpdated(String filter);

    }

    static final class ViewLookupTable {

        @InjectView(R.id.cityFilterTxt)
        TextView cityFilterTxt;

        public ViewLookupTable(View view) {
            ButterKnife.inject(this, view);
        }

    }

}
