package com.familyplan.ihealth.fragment;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentMy extends NativeFragment {
    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("我");
    }
}
