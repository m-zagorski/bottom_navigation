package com.example.mateusz.bottomnavigation

import android.content.Context
import android.graphics.Outline
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        bezier_test.background = BottomNavigationDrawable(this)
        bottom_navigation.background = BottomNavigationDrawable(this)
//        bottom_navigation.outlineProvider = OutlineProvider(this)
//        bottom_navigation.clipToOutline = true
    }
}



class OutlineProvider(private val context: Context): ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        Log.e("OutlineShit", " " + view)
        val scaleFactor = context.resources.displayMetrics.density

        outline.setRect(0, (11*scaleFactor).toInt(), view.width, view.height)

    }
}