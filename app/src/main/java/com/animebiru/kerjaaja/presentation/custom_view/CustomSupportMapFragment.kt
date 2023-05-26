package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.SupportMapFragment

class CustomSupportMapFragment : SupportMapFragment() {

    fun interface CustomSupportMapFragmentOnTouchListener {
        fun onTouch()
    }

    var touchListener: CustomSupportMapFragmentOnTouchListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = super.onCreateView(inflater, container, savedInstanceState)
        val frameLayout = TouchableWrapper(requireContext())
        frameLayout.setBackgroundColor(Color.TRANSPARENT)
        (layout as ViewGroup).addView( frameLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return layout
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> touchListener?.onTouch()
                MotionEvent.ACTION_UP -> touchListener?.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }

}