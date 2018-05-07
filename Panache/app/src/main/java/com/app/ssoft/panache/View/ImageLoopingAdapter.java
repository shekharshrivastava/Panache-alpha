package com.app.ssoft.panache.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.ssoft.panache.R;
import com.asksira.loopingviewpager.LoopingPagerAdapter;

import java.util.ArrayList;

public class ImageLoopingAdapter extends LoopingPagerAdapter<Integer> {
    private static final int VIEW_TYPE_NORMAL = 100;
    private static final int VIEW_TYPE_SPECIAL = 101;

    public ImageLoopingAdapter(Context context, ArrayList<Integer> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }
    @Override
    protected int getItemViewType(int listPosition) {
        if (itemList.get(listPosition) == 0) return VIEW_TYPE_SPECIAL;
        return VIEW_TYPE_NORMAL;
    }

    @Override
    protected View inflateView(int viewType, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.item_pager, null);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        convertView.findViewById(R.id.image).setBackground(context.getResources().getDrawable(getBackgroundColor(listPosition)));
        TextView description = convertView.findViewById(R.id.description);
        description.setText(String.valueOf(itemList.get(listPosition)));
    }

    private int getBackgroundColor (int number) {
        switch (number) {
            case 0:
                return R.drawable.blur_1;
            case 1:
                return R.drawable.blur_2;
            case 2:
                return R.drawable.blur_3;
            case 3:
                return R.drawable.blur_4;
            case 4:
                return R.drawable.blur_5;
            case 5:
                return R.drawable.blur_6;
            default:
                return R.drawable.blur_1;
        }
    }

}
