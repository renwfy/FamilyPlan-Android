package com.familyplan.ihealth.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Alessandro on 12/01/2016.
 */
public class EditDescriptTouchHelperCallback extends ItemTouchHelper.Callback {
    public interface CallbackItemTouch {
        void itemTouchOnMove(int oldPosition,int newPosition);
    }

    CallbackItemTouch callbackItemTouch;

    public EditDescriptTouchHelperCallback(CallbackItemTouch callbackItemTouch){
        this.callbackItemTouch = callbackItemTouch;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // swiped disabled
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeFlag( ItemTouchHelper.ACTION_STATE_DRAG , dragFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        callbackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }
}
