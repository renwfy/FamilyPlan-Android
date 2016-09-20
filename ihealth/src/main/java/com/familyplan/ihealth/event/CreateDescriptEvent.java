package com.familyplan.ihealth.event;

import com.familyplan.ihealth.model.Descript;

import java.util.List;

/**
 * Created by LSD on 16/8/9.
 */
public class CreateDescriptEvent {
    public Descript descript;

    public CreateDescriptEvent(Descript descript) {
        this.descript = descript;
    }
}
