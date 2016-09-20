package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.model.Descript;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Alessandro on 12/01/2016.
 */
public class EditDescriptRecyclerAdapter extends RecyclerView.Adapter<EditDescriptRecyclerAdapter.MViewHolder> {
    Context context;
    OnStartDragListener dragStartListener;
    private List<Descript> mList;

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public EditDescriptRecyclerAdapter(Context context, List<Descript> mList, OnStartDragListener dragStartListener) {
        this.mList = mList;
        this.context = context;
        this.dragStartListener = dragStartListener;
    }

    public List<Descript> getMList() {
        return mList;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_editdescript_item, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        Descript descript = mList.get(position);
        holder.myItemText.setText(descript.getText());
        Picasso.with(context).load(descript.getImg() + "?imageView2/5/w/400/h/400").into(holder.ivImage);
        holder.layoutDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.layoutRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                EditDescriptRecyclerAdapter.this.notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_remove)
        LinearLayout layoutRemove;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.my_item_text)
        TextView myItemText;
        @BindView(R.id.layout_drag)
        LinearLayout layoutDrag;

        public MViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
