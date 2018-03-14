package com.example.mateusz.bottomnavigation

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.IntRange
import android.support.v4.content.ContextCompat


class BottomNavigationDrawable(private val context: Context) : Drawable() {

    private var needCalculatePath = true
    private val paint = Paint().apply {
        strokeWidth = 0F
        isAntiAlias = true
        color = Color.WHITE
    }
    private val shadowPaint = Paint().apply {
        strokeWidth = context.resources.getDimensionPixelSize(R.dimen.navigation_shadow).toFloat()
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.start_shadow)
        maskFilter = BlurMaskFilter(6F, BlurMaskFilter.Blur.NORMAL)
    }
    private val path = Path()
    private val shadowPath = Path()

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()
        val scaleFactor = context.resources.displayMetrics.density
        if (needCalculatePath) {
            path.reset()

            val x1 = width / 2 - 28F * scaleFactor
            val x2 = width / 2 + 28F * scaleFactor
            val y1 = 11F * scaleFactor
            val y2 = 11F * scaleFactor
            val curveRadius = 88F

            val midX = x1 + (x2 - x1) / 2
            val midY = y1 + (y2 - y1) / 2
            val angle = Math.atan2((midY - y1).toDouble(), (midX - x1).toDouble()) * (180 / Math.PI) - 90
            val angleRadians = Math.toRadians(angle)
            val pointX = (midX + curveRadius * Math.cos(angleRadians))
            val pointY = (midY + curveRadius * Math.sin(angleRadians))

            path.moveTo(0F, y1)
            path.lineTo(x1, y1)
            path.cubicTo(x1, y1, pointX.toFloat(), pointY.toFloat(), x2, y2)
            path.lineTo(width, y1)
            path.lineTo(width, height)
            path.lineTo(0F, height)
            path.lineTo(0F, y1)


            val x11 = width / 2 - 28F * scaleFactor
            val x21 = width / 2 + 28F * scaleFactor
            val y11 = 11F * scaleFactor
            val y21 = 11F * scaleFactor
            val curveRadius1 = 86F

            val midX1 = x11 + (x21 - x11) / 2
            val midY1 = y11 + (y21 - y11) / 2
            val angle1 = Math.atan2((midY1 - y11).toDouble(), (midX1 - x11).toDouble()) * (180 / Math.PI) - 90
            val angleRadians1 = Math.toRadians(angle1)
            val pointX1 = (midX1 + curveRadius1 * Math.cos(angleRadians1))
            val pointY1 = (midY1 + curveRadius1 * Math.sin(angleRadians1))

            shadowPath.moveTo(0F, 11F * scaleFactor)
            shadowPath.lineTo(x1, 11F * scaleFactor)
            shadowPath.cubicTo(x11, y11, pointX1.toFloat(), pointY1.toFloat(), x21, y21)
            shadowPath.lineTo(width, 11F * scaleFactor)
        }

//        val clipBounds = canvas.clipBounds
//        clipBounds.inset(0, -10)
//        canvas.clipRect(clipBounds, Region.Op.REPLACE)

        canvas.drawPath(path, paint)
//        shadowPaint.shader = LinearGradient(0F, height, 0F, 0F, ContextCompat.getColor(context, R.color.start_shadow), ContextCompat.getColor(context, R.color.end_shadow), Shader.TileMode.MIRROR)
        canvas.drawPath(shadowPath, shadowPaint)
//        canvas.drawLine(0F, 10F*scaleFactor, width, 10F*scaleFactor, shadowPaint)

//        val p = Paint()
//        p.shader = LinearGradient(0F, context.resources.getDimensionPixelSize(R.dimen.navigation_shadow).toFloat(), 0F, 0F, ContextCompat.getColor(context, R.color.shadow), Color.WHITE, Shader.TileMode.MIRROR)
//        canvas.drawPaint(p)
    }


    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        needCalculatePath = true
    }

    override fun setAlpha(@IntRange(from = 0, to = 255) alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

}


//            path.moveTo(0F, arcHeight * scaleFactor)
//            path.lineTo(width / 2 - 32F * scaleFactor, arcHeight * scaleFactor)
//
////            path.lineTo(width / 2 - 32F * scaleFactor, 0F)
////            path.lineTo(width / 2 + 32F * scaleFactor, 0F)
////            path.lineTo(width / 2 + 32F * scaleFactor, 8F * scaleFactor)
//
//            path.arcTo(RectF(width / 2 - 32F * scaleFactor, 0F, width / 2 + 32F * scaleFactor, 16F * scaleFactor), 180F, 180F)
//
//
////            path.cubicTo(width / 2 - 16F * scaleFactor, arcHeight/2 * scaleFactor, width / 2, arcHeight/2 * scaleFactor, width / 2 + 16F * scaleFactor, arcHeight * scaleFactor)
//
//
//            path.moveTo(width/2+40F*scaleFactor, arcHeight*scaleFactor)
//            path.lineTo(width, arcHeight * scaleFactor)
//            path.lineTo(width, height)
//            path.lineTo(0F, height)
//            path.lineTo(0F, arcHeight * scaleFactor)


//            path.lineTo(width/2-30F*scaleFactor, height)
//            path.lineTo(0F, height)


//            val width = 128F*scaleFactor
//            path.moveTo(width / 2 - 32F * scaleFactor, bezierHeight)
//            path.cubicTo(width / 4, bezierHeight, width / 4, 0F, width / 2, 0F)
//            path.cubicTo((width / 4) * 3, 0F, (width / 4) * 3, bezierHeight, width, bezierHeight)