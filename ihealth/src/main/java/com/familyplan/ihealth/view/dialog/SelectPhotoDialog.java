package com.familyplan.ihealth.view.dialog;

import android.content.Context;
import android.widget.TextView;

import com.familyplan.ihealth.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectPhotoDialog extends AbsDialog {
    @BindView(R.id.tv_dialog_title)
    TextView title;

    public SelectPhotoDialog(Context context) {
        super(context,R.layout.dialog_select_photo,R.style.dialogSelectPhoto);
        title.setText("选择图片");
    }

    @OnClick(R.id.ll_dialog_scan)
    public void onGallery() {
        this.dismiss();
    }

    @OnClick(R.id.ll_dialog_take)
    public void onCamera() {
        this.dismiss();
    }

    @OnClick(R.id.layout)
    public void close() {
        this.dismiss();
    }
}