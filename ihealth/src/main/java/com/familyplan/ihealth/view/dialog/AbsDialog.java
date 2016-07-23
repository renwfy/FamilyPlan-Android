package com.familyplan.ihealth.view.dialog;

import android.app.Dialog;
import android.content.Context;

import com.familyplan.ihealth.R;

import butterknife.ButterKnife;

public class AbsDialog extends Dialog {

    public AbsDialog(Context context, int layout) {
        this(context, layout, R.style.dialog);
    }

    public AbsDialog(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        ButterKnife.bind(this);
    }
}
