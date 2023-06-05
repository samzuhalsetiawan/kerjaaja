package com.animebiru.kerjaaja.presentation.listener

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

abstract class AppBarStateChangeListener : OnOffsetChangedListener {

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState: State = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout == null) return
        currentState = when {
            verticalOffset == 0 -> {
                if (currentState != State.EXPANDED) onAppBarStateChange(appBarLayout,
                    State.EXPANDED
                )
                State.EXPANDED
            }

            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (currentState != State.COLLAPSED) onAppBarStateChange(appBarLayout,
                    State.COLLAPSED
                )
                State.COLLAPSED
            }

            else -> {
                if (currentState != State.IDLE) onAppBarStateChange(appBarLayout, State.IDLE)
                State.IDLE
            }
        }
    }

    abstract fun onAppBarStateChange(appBarLayout: AppBarLayout, state: State)

}