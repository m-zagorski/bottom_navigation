package com.example.mateusz.bottomnavigation

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_bottom_navigation.view.*

class BottomNavigationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ViewGroup(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_bottom_navigation, this, true)
    }

    private val product = navigation_product
    private val saved = navigation_saved
    private val stores = navigation_stores
    private val more = navigation_more
    private val painter = navigation_painter

    private val dashHeight = context.resources.getDimensionPixelSize(R.dimen.navigation_height).toFloat()
    private val downValue = context.resources.getDimensionPixelSize(R.dimen.navigation_painter_down_value)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var childState = 0

        measureChildWithMargins(painter,
                widthMeasureSpec, 0,
                heightMeasureSpec, 0)

        val heightWithoutDash = MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(heightMeasureSpec) - dashHeight).toInt(), MeasureSpec.EXACTLY)
        val totalWidthLeft = MeasureSpec.getSize(widthMeasureSpec) - painter.measuredWidthWithMargins
        val itemWidth = totalWidthLeft / 4

        product.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), heightWithoutDash)
        saved.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), heightWithoutDash)
        stores.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), heightWithoutDash)
        more.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), heightWithoutDash)

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        var currentTop = paddingTop + dashHeight.toInt()
        var left = paddingStart

        product.layoutView(left, currentTop, width)
        left += product.measuredWidthWithMargins
        saved.layoutView(left, currentTop, width)
        left += saved.measuredWidthWithMargins
        painter.layoutView(left, b - t - painter.measuredHeightWithMargins + downValue, width)
        left += painter.measuredWidthWithMargins
        stores.layoutView(left, currentTop, width)
        left += stores.measuredWidthWithMargins
        more.layoutView(left, currentTop, width)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams = ViewGroup.MarginLayoutParams(context, attrs)

    override fun shouldDelayChildPressedState() = false
}


internal val View.measuredWidthWithMargins: Int
    get() = measuredWidth + (layoutParams as ViewGroup.MarginLayoutParams).leftMargin + (layoutParams as ViewGroup.MarginLayoutParams).rightMargin

internal val View.measuredHeightWithMargins: Int
    get() = measuredHeight + (layoutParams as ViewGroup.MarginLayoutParams).topMargin + (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin

internal val View.layoutRtl: Boolean
    get() = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

internal val View.marginStart: Int
    get() = ((layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: 0)

internal val View.marginEnd: Int
    get() = ((layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: 0)

internal val View.marginTop: Int
    get() = ((layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0)

internal fun View.layoutView(start: Int, top: Int, parentWidth: Int) {
    val leftWithMargins = if (layoutRtl) parentWidth - start - marginStart - measuredWidth else start + marginStart
    val topWithMargins = top + marginTop
    layout(
            leftWithMargins,
            topWithMargins,
            leftWithMargins + measuredWidth,
            topWithMargins + measuredHeight)
}

infix fun View.maxHeight(another: View): Int = Math.max(measuredHeightWithMargins, another.measuredHeightWithMargins)
infix fun View.maxHeight(anotherHeight: Int): Int = Math.max(measuredHeightWithMargins, anotherHeight)
infix fun View.maxWidth(another: View): Int = Math.max(measuredWidthWithMargins, another.measuredWidthWithMargins)
infix fun View.maxWidth(anotherWidth: Int): Int = Math.max(measuredWidthWithMargins, anotherWidth)