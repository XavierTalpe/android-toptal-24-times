package be.xvrt.times.dialog;

import android.content.DialogInterface;

public class DialogDismissOnClickListener implements DialogInterface.OnClickListener {

  @Override
  public void onClick( DialogInterface dialog, int which ) {
    dialog.dismiss();
  }

}
