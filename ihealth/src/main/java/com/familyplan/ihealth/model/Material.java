package com.familyplan.ihealth.model;

import java.io.Serializable;

/**
 * Created by LSD on 16/7/31.
 */
public class Material implements Serializable{

    /**
     * name : 里脊肉
     * measure : 适量
     * unit :
     */

    private String name="";
    private String measure="";
    private String unit="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
