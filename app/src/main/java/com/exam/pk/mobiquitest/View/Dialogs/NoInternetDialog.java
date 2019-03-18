package com.exam.pk.mobiquitest.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.exam.pk.mobiquitest.R;
import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NoInternetDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_internet_text)
                .setPositiveButton(R.string.retry_btn, (dialog, which) -> {
                    ((ListPageActivity)getActivity()).refreshData();
                }).setNegativeButton(R.string.settings_btn, (dialog, which) -> {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        });

        return builder.create();
    }
}
