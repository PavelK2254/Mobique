package com.exam.pk.mobiquitest.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.exam.pk.mobiquitest.R;
import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ServerErrorDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.server_error)
                .setPositiveButton(R.string.retry_btn, (dialog, which) -> {
                    ((ListPageActivity)getActivity()).refreshData();
                }).setNegativeButton(R.string.leave_btn, (dialog, which) -> {
                    getActivity().finish();
                });

        return builder.create();
    }
}
