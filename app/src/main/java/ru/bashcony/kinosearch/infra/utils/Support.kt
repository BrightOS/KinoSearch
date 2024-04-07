package ru.bashcony.kinosearch.infra.utils

import android.animation.ObjectAnimator
import android.widget.TextView


private fun expandTextView(tv: TextView) {
    val animation = ObjectAnimator.ofInt(tv, "maxLines", tv.lineCount)
    animation.setDuration(200).start()
}

private fun collapseTextView(tv: TextView, numLines: Int) {
    val animation = ObjectAnimator.ofInt(tv, "maxLines", numLines)
    animation.setDuration(200).start()
}