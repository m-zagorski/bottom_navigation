package com.example.mateusz.bottomnavigation

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import android.widget.ImageView


class ForegroundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    private var foregroundDrawable: Drawable? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundLP)

        val d = a.getDrawable(R.styleable.ForegroundLP_android_foreground)
        if (d != null) {
            setForegroundCurrent(d)
        }
        a.recycle()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = ViewOutlineProvider.BOUNDS
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foregroundDrawable?.setBounds(0, 0, w, h)
    }

    override fun hasOverlappingRendering(): Boolean {
        return false
    }

    override  fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === foreground
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        foregroundDrawable?.jumpToCurrentState()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        foregroundDrawable?.let {
            if(it.isStateful) it.state = drawableState
        }
    }

    /**
     * Returns the drawable used as the foreground of this view. The
     * foreground drawable, if non-null, is always drawn on top of the children.
     *
     * @return A Drawable or null if no foreground was set.
     */
    override fun getForeground(): Drawable? {
        return foregroundDrawable
    }

    /**
     * Supply a Drawable that is to be rendered on top of the contents of this ImageView
     *
     * @param drawable The Drawable to be drawn on top of the ImageView
     */
    fun setForegroundCurrent(drawable: Drawable) {
        if (foregroundDrawable != drawable) {
            foregroundDrawable?.let {
                it.callback = null
                unscheduleDrawable(it)
            }
            foregroundDrawable = drawable

            foregroundDrawable?.let {
                it.setBounds(0, 0, width, height)
                setWillNotDraw(false)
                it.callback = this
                if(it.isStateful) it.state = drawableState
            } ?: setWillNotDraw(true)

            invalidate()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        foregroundDrawable?.draw(canvas)
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        foregroundDrawable?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) it.setHotspot(x, y)
        }
    }

}