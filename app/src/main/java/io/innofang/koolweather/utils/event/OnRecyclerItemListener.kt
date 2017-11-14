package io.innofang.koolweather.utils.event

import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Author: Inno Fang
 * Time: 2017/6/26 17:18
 * Description:
 */

abstract class OnRecyclerItemListener(var recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    var mGestureDetector: GestureDetectorCompat

    init {
        mGestureDetector = GestureDetectorCompat(recyclerView.context, ItemGestureListener())
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        mGestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    inner class ItemGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            val child = recyclerView.findChildViewUnder(e!!.x, e.y)
            if (null != child) {
                val vh = recyclerView.getChildViewHolder(child)
                onItemClick(vh)
            }
            return true
        }

    }

    abstract fun onItemClick(vh: RecyclerView.ViewHolder)

}