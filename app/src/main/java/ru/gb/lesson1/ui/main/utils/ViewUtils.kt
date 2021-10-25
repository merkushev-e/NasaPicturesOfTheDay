package ru.gb.lesson1.ui.main.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar
        .make(this, text, length)
        .setAction(actionText) { action(this) }
        .show()
}
