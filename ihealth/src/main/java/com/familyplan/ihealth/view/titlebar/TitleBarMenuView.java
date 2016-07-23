package com.familyplan.ihealth.view.titlebar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.lib.adapter.SimpleAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class TitleBarMenuView {
    private List<TitleBarMenuItem> itemList;
    private Context context;
    private PopupWindow popupWindow;

    @BindView(R.id.lv_title_menu)
    ListView listView;

    public TitleBarMenuView(final Context context, final List<TitleBarMenuItem> itemList) {
        this.context = context;
        this.itemList = itemList;

        View view = LayoutInflater.from(context).inflate(R.layout.view_title_menu, null);
        ButterKnife.bind(this, view);
        //设置 listview
        listView.setAdapter(new TitleBarAdapter(context, itemList));

        popupWindow = new PopupWindow(view,
                context.getResources().getDimensionPixelSize(R.dimen.title_menu_width),
                LayoutParams.WRAP_CONTENT);
        // 使其聚集
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(null);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
    }

    //设置菜单项点击监听器
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        //this.listener = listener;
        listView.setOnItemClickListener(listener);
    }

    //下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(parent, 0, context.getResources().getDimensionPixelSize(R.dimen.margin_common_small) + 2);
            //保证尺寸是根据屏幕像素密度来的
            //context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
            //刷新状态
            popupWindow.update();
        }
    }

    //隐藏菜单
    public void dismiss() {
        popupWindow.dismiss();
    }

    // 适配器
    class TitleBarAdapter extends SimpleAdapter<TitleBarMenuItem> {
        public TitleBarAdapter(Context context, List<TitleBarMenuItem> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, TitleBarMenuItem item, View convertView) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = holder.view;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(item.getTitle());
            Picasso.with(context).load(item.getIcon()).into(holder.icon);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_title_menu_name)
            TextView name;

            @BindView(R.id.iv_title_menu_icon)
            ImageView icon;

            View view;

            public ViewHolder() {
                view = LayoutInflater.from(context).inflate(R.layout.view_title_menu_item, null);
                ButterKnife.bind(this, view);
            }
        }
    }
}
