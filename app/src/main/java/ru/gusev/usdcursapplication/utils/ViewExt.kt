package ru.gusev.usdcursapplication.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.show(needShow: Boolean = true) {
    visibility = if(needShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.hide(needShow: Boolean = false) {
    visibility = if(needShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.hideKeyboard() {
    this.apply {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }
}