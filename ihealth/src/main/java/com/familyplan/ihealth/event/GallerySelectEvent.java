package com.familyplan.ihealth.event;

import java.util.ArrayList;

/**
 * Created by LSD on 16/8/2.
 */
public class GallerySelectEvent {
    public ArrayList<String> paths;
    public GallerySelectEvent(ArrayList<String> paths){
        this.paths = paths;
    }
}
