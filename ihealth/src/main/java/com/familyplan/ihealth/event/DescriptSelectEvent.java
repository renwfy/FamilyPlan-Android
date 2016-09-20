package com.familyplan.ihealth.event;

import java.util.ArrayList;

/**
 * Created by LSD on 16/8/2.
 */
public class DescriptSelectEvent {
    public ArrayList<String> paths;
    public DescriptSelectEvent(ArrayList<String> paths){
        this.paths = paths;
    }
}
