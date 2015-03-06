package com.example.soloviev.calculator;

import android.app.AlertDialog;

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by user_2 on 06.03.2015.
 */
public class DialogFragments extends DialogFragment {
    static final String TAG_DIALOG = "dialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("division")
                .setMessage("division by zero")
                .setNeutralButton("ok", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static final String ARG_M = "msg";

    public static DialogFragments newInstance(CharSequence message) {
        Bundle args = new Bundle(1);
        args.putCharSequence(ARG_M, message);
        DialogFragments dialogFragments = new DialogFragments();
        dialogFragments.setArguments(args);
        return dialogFragments;
    }

}