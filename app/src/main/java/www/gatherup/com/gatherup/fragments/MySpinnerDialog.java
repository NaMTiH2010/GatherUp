package www.gatherup.com.gatherup.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Matthew Luce on 5/11/2017.
 */

public class MySpinnerDialog extends DialogFragment {
    public final String TAG ="MySpinnerDialog";
    public MySpinnerDialog() {
        // use empty constructors. If something is needed use onCreate's
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog _dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme()); // You can use styles or inflate a view
        _dialog.setMessage("Spinning.."); // set your messages if not inflated from XML

        _dialog.setCancelable(false);

        return _dialog;
    }

}