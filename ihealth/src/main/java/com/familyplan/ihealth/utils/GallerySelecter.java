package com.familyplan.ihealth.utils;

import com.familyplan.ihealth.activity.AbstractActivity;
import com.familyplan.ihealth.view.dialog.SelectPhotoDialog;

/**
 * Created by LSD on 16/8/13.
 */
public class GallerySelecter {
    public static void start(final AbstractActivity activity, final boolean needCorop) {
        new SelectPhotoDialog(activity) {
            @Override
            public void onCamera() {
                super.onCamera();
                GalleryManager.startFromCamra(activity, needCorop);
            }

            @Override
            public void onGallery() {
                super.onGallery();
                GalleryManager.startFromAlbum(activity, needCorop);
            }
        }.show();
    }
}
