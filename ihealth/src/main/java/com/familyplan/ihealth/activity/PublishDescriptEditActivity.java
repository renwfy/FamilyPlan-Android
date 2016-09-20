package com.familyplan.ihealth.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.adapter.EditDescriptRecyclerAdapter;
import com.familyplan.ihealth.event.CreateDescriptEvent;
import com.familyplan.ihealth.event.EditDescriptEvent;
import com.familyplan.ihealth.utils.EditDescriptTouchHelperCallback;
import com.familyplan.ihealth.model.Descript;
import com.familyplan.ihealth.utils.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LSD on 16/8/9.
 */
public class PublishDescriptEditActivity extends CommonActivity {
    EditDescriptRecyclerAdapter myAdapter;
    ItemTouchHelper touchHelper;

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_publishdescript_edit;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("编辑步骤");
        setRightTxt("保存");

        List<Descript> mList = (List<Descript>) getIntent().getSerializableExtra("descript");
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        myAdapter = new EditDescriptRecyclerAdapter(mActivity, mList, new EditDescriptRecyclerAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                touchHelper.startDrag(viewHolder);
            }
        });
        myRecyclerView.setAdapter(myAdapter);
        ItemTouchHelper.Callback callback = new EditDescriptTouchHelperCallback(new EditDescriptTouchHelperCallback.CallbackItemTouch() {
            @Override
            public void itemTouchOnMove(int oldPosition, int newPosition) {
                Collections.swap(myAdapter.getMList(), oldPosition, newPosition);
                myAdapter.notifyItemMoved(oldPosition, newPosition);
            }
        });
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(myRecyclerView);
    }

    @Override
    public void onRightClick() {
        EventBus.getDefault().post(new EditDescriptEvent(myAdapter.getMList()));
        finish();
    }

    // 隐藏输入法
    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissKeyboard();
    }
}
