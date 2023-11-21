package com.technical_challenge.github.ui.list.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeLeftItemTouchHelper(
    private val onSwipeToLeft: (position: Int) -> Unit
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) =
        onSwipeToLeft(viewHolder.bindingAdapterPosition)

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
            val itemView = viewHolder.itemView
            val paint = Paint().apply {
                color = Color.RED
            }
            val icon = ContextCompat.getDrawable(recyclerView.context, androidx.core.R.drawable.ic_call_answer)
            val iconMargin = (itemView.height - (icon?.intrinsicHeight ?: 0)) / 2
            val iconTop = itemView.top + (itemView.height - (icon?.intrinsicHeight ?: 0)) / 2
            val iconBottom = iconTop + (icon?.intrinsicHeight ?: 0)

            c.drawRect(
                itemView.right.toFloat() + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat(),
                paint
            )

            icon?.let {
                val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                icon.draw(c)
            }
        }

        val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width.toFloat()
        viewHolder.itemView.alpha = alpha

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}