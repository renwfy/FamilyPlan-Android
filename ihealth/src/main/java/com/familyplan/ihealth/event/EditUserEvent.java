package com.familyplan.ihealth.event;

import com.familyplan.ihealth.model.User;

/**
 * Created by LSD on 16/8/6.
 */
public class EditUserEvent {
    public User user;
    public EditUserEvent(User user){
        this.user = user;
    }
}
