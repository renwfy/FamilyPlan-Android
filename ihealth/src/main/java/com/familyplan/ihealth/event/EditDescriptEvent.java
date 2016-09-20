package com.familyplan.ihealth.event;

import com.familyplan.ihealth.model.Descript;

import java.util.List;

/**
 * Created by LSD on 16/8/9.
 */
public class EditDescriptEvent {
    public List<Descript> descriptList;

    public EditDescriptEvent(List<Descript> descriptList) {
        this.descriptList = descriptList;
    }
}
