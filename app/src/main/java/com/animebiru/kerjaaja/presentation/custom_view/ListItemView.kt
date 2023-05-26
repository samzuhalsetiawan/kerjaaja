package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.utils.ExtensionFunctions.dp
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.imageview.ShapeableImageView
import kotlin.math.roundToInt

class ListItemView: FrameLayout {

    @DrawableRes
    var itemIcon: Int = R.drawable.ic_person_24
        set(value) {
            field = value
            rootView.findViewById<ShapeableImageView>(R.id.ivItemIcon)?.apply {
                setImageResource(value)
            }
        }

    @DrawableRes
    var itemActionIcon: Int = R.drawable.ic_arrow_right_24px
        set(value) {
            field = value
            rootView.findViewById<ShapeableImageView>(R.id.ivItemActionIcon)?.apply {
                setImageResource(value)
            }
        }

    var labelText: String = resources.getString(R.string.placeholder)
        set(value) {
            field = value
            rootView.findViewById<TextView>(R.id.tvItemLabel)?.apply {
                text = value
            }
        }

    var subtitleText: String? = null
        set(value) {
            field = value
            val tvSubtitle = rootView.findViewById<TextView>(R.id.tvItemSubtitle)
            if (value == null){
                tvSubtitle.visibility = View.GONE
            } else {
                tvSubtitle.visibility = View.VISIBLE
                tvSubtitle.apply { text = value }
            }
        }

    var isSingleLineSubtitle: Boolean = false
        set(value) {
            field = value
            if (value) changeToSingleLineSubtitleLayout() else changeToMultiLineSubtitleLayout()
        }

    var useBottomDivider: Boolean = true
        set(value) {
            field = value
            val bottomDivider = rootView.findViewById<MaterialDivider>(R.id.dividerBottom)
            if (value) {
                bottomDivider?.visibility = View.VISIBLE
            } else {
                bottomDivider?.visibility = View.GONE
            }
        }

    constructor(context: Context): super(context) {
        initView(context)
    }
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListItemView)
        initView(context, typedArray)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListItemView, defStyleAttr, 0)
        initView(context, typedArray)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListItemView, defStyleAttr, defStyleRes)
        initView(context, typedArray)
    }

    private fun initView(context: Context): View {
        return inflate(context, R.layout.list_item_view, this)
    }

    private fun initView(context: Context, typedArray: TypedArray) {
        initView(context)
        itemIcon = typedArray.getResourceId(R.styleable.ListItemView_itemIcon, R.drawable.ic_person_24)
        itemActionIcon = typedArray.getResourceId(R.styleable.ListItemView_itemActionIcon, R.drawable.ic_arrow_right_24px)
        labelText = typedArray.getString(R.styleable.ListItemView_labelText) ?: labelText
        subtitleText = typedArray.getString(R.styleable.ListItemView_subtitleText) ?: subtitleText
        isSingleLineSubtitle = typedArray.getBoolean(R.styleable.ListItemView_isSingleLineSubtitle, false)
        useBottomDivider = typedArray.getBoolean((R.styleable.ListItemView_useBottomDivider), true)
        typedArray.recycle()
    }

    private fun changeToSingleLineSubtitleLayout() {
        val constraintLayout = rootView.findViewById<ConstraintLayout>(R.id.clListItemView)
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(R.id.ivItemIcon, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.ivItemIcon, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            connect(R.id.ivItemIcon, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(R.id.ivItemActionIcon, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.ivItemActionIcon, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
            connect(R.id.ivItemActionIcon, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        }
        constraintSet.applyTo(constraintLayout)
        rootView.findViewById<TextView>(R.id.tvItemSubtitle)?.apply { maxLines = 1 }
    }

    private fun changeToMultiLineSubtitleLayout() {
        val constraintLayout = rootView.findViewById<ConstraintLayout>(R.id.clListItemView)
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(R.id.ivItemIcon, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.ivItemIcon, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            clear(R.id.ivItemIcon, ConstraintSet.BOTTOM)
            connect(R.id.ivItemActionIcon, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.ivItemActionIcon, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
            clear(R.id.ivItemActionIcon, ConstraintSet.BOTTOM)
        }
        rootView.findViewById<TextView>(R.id.tvItemSubtitle)?.apply { maxLines = 2 }
        constraintSet.applyTo(constraintLayout)
    }

}