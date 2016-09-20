package com.familyplan.ihealth.event;

import com.familyplan.ihealth.model.Material;

import java.util.List;

/**
 * Created by LSD on 16/8/9.
 */
public class CreateMaterialEvent {
    public List<Material> materialList;

    public CreateMaterialEvent(List<Material> materialList) {
        this.materialList = materialList;
    }
}
