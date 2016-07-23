package com.familyplan.ihealth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private String Tag = "BaseFragment";
    FragmentActivity mContext;
	protected View rootView;//缓存Fragment view

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(getFrameLayout(), null);
            onViewCreated();
        }
        //缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {  
            parent.removeView(rootView);  
        }
        return rootView;
    }

    //用于友盟统计的描述
    protected String getDisplayTitle(){
        return "";
    }
    protected abstract int getFrameLayout();
    protected void onViewCreated(){
    }

    @Override
    public void onResume() {
        super.onResume();
        String displayTitle = getDisplayTitle();
        if (!TextUtils.isEmpty(displayTitle)){
        }else{
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        String displayTitle = getDisplayTitle();
        if (!TextUtils.isEmpty(displayTitle)){
        }else{
        }
    }
}
