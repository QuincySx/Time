package com.smallraw.time.base

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.smallraw.time.R
import qiu.niorgai.StatusBarCompat


abstract class BaseActivity : AppCompatActivity(), Handler.Callback {
    companion object {
        private val HANDLER_MSG_PROMPT = 1
    }

    protected val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (useStatusBar()) {
            val color = ResourcesCompat.getColor(resources, selfStatusBarColor(), null)
            setStatusBarColor(color)
        }
    }

    open fun useStatusBar() = true

    @ColorRes
    open fun selfStatusBarColor() = R.color.white

    protected fun setStatusBarColor(@ColorInt color: Int) {
        StatusBarCompat.setStatusBarColor(this, color)
        if (color == Color.WHITE) {
            StatusBarLightMode(this)
        }
    }

    fun showPrompt(@StringRes res: Int) {
        showPrompt(getString(res))
    }

    fun showPrompt(msg: String) {
        val msg = Message.obtain()
        msg.what = HANDLER_MSG_PROMPT
        msg.obj = msg
        mHandler.sendMessage(msg)
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            HANDLER_MSG_PROMPT -> {
                Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    fun StatusBarLightMode(activity: Activity): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(window, true)) {
                result = 1
            } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                result = 2
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
        }
        return result
    }

    fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window!!.getAttributes()
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window!!.setAttributes(lp)
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }
}
