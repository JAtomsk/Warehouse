/**
 * ItemDecoration
 *
 * Purpose:
 * Adds uniform spacing between items in a RecyclerView. This class is used to improve the visual appearance
 * of RecyclerView items by ensuring there is consistent padding around them.
 *
 * How it Works:
 * - It applies uniform spacing (padding) to the left, right, and bottom sides of each item.
 * - For the first item in the RecyclerView, it also applies spacing to the top to ensure consistent padding around the item.
 *
 * Usage:
 * Add an instance of this class to a RecyclerView using the addItemDecoration() method to apply consistent spacing
 * between items within the RecyclerView.
 *
 * Example:
 * int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
 * recyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
 *
 * Author: [Jared Semonin]
 * Date: [03/31/2024]
 * Version: 1.0
 */



package com.semonin.jjwarehouse;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    // Constructor taking the size of the space to be applied as padding around the items

    public ItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Apply the specified spacing as padding around the item

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
