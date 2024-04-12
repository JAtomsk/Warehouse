/**
 * ItemDecoration for RecyclerView
 *
 * Purpose:
 * Enhances the visual aesthetics of RecyclerView by adding uniform spacing between items.
 * This class is critical for achieving consistent padding and margins in lists, grids, or any collections managed by RecyclerView.
 *
 * How it Works:
 * - It assigns specified padding (space) to all sides of an item view except the top side of subsequent items to prevent doubling of space.
 * - The first item receives top padding to ensure uniformity from the collection's start.
 *
 * Usage:
 * Utilize this decorator by attaching it to a RecyclerView to ensure each item is distinctly separated, enhancing readability and design.
 * Example usage within a Fragment or Activity:
 *   int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
 *   recyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
 *
 * Author: Jared Semonin
 * Date: 04/11/2024
 * Version: 2.0
 */

package com.semonin.jjwarehouse;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    /**
     * Constructor to set the spacing size.
     * @param space Integer value representing the pixels to be used as space around each item.
     */
    public ItemDecoration(int space) {
        this.space = space;
    }

    /**
     * Determines the size and location of the offset to apply to each item within the RecyclerView.
     * This method is automatically invoked by the RecyclerView to retrieve each item's offsets.
     *
     * @param outRect Rect to receive the output.
     * @param view The child view to decorate
     * @param parent RecyclerView this ItemDecoration is applied to
     * @param state The current state of RecyclerView
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Always apply left, right, and bottom spacing
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Apply top spacing only for the first item to maintain uniform spacing from the start
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
