package ru.skillbranch.profile.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View


fun Activity.hideKeyboard()
{
    hideKeyboard(currentFocus ?: View(this))
}

fun Activity.isKeyboardOpen(): Boolean
{
    val rootView = this.window.decorView
    /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
    val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
    val heightDiff: Int = rootView.bottom - r.bottom
    /* Threshold size: dp to pixels, multiply with display density */
    return heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
}

fun Activity.isKeyboardClosed(): Boolean = !this.isKeyboardOpen()