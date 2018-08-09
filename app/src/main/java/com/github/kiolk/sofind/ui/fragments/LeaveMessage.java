package com.github.kiolk.sofind.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.github.kiolk.sofind.R;

/**
 * Dialog fragment that implement leaving from application logic
 */
public class LeaveMessage extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ARE_YOU_WANT_LEAV).setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                getActivity().finish();
            }
        }).setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                //
            }
        });
        return builder.create();
    }
}
