package ru.bashcony.kinosearch.infra.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import java.time.OffsetDateTime
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern


private fun expandTextView(tv: TextView) {
    val animation = ObjectAnimator.ofInt(tv, "maxLines", tv.lineCount)
    animation.setDuration(200).start()
}

private fun collapseTextView(tv: TextView, numLines: Int) {
    val animation = ObjectAnimator.ofInt(tv, "maxLines", numLines)
    animation.setDuration(200).start()
}

val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    )

val Number.px
    get() = (this.toFloat() / Resources.getSystem().displayMetrics.density)

val String.date
    get() = Date.from(
        OffsetDateTime.parse(this)
            .toInstant()
    )

val Date.russianStringWithoutHour
    get() = DateFormat.format("dd MMMM yyyy", this)

val Date.russianStringWithHour
    get() = DateFormat.format("dd MMM yyyy в HH:mm", this)

val typeToRussian = mapOf(
    "animated-series" to "мультсериал",
    "anime" to "аниме",
    "cartoon" to "мультфильм",
    "movie" to "фильм",
    "tv-series" to "сериал"
)

fun parseYouTubeId(url: String): String? {
    val regExp =
        "^.*((youtu.be/)|(v/)|(/u/\\w/)|(embed/)|(watch\\?))\\??v?=?([^#&?]*).*"
    val pattern: Pattern = Pattern.compile(regExp)
    val matcher: Matcher = pattern.matcher(url)
    return if (matcher.matches() && matcher.group(7) != null && matcher.group(7)!!.length == 11)
        matcher.group(7)
    else
        null
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.registerDataObserver(onDataChanged: () -> Unit) {
    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            onDataChanged.invoke()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            onDataChanged.invoke()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            onDataChanged.invoke()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onDataChanged.invoke()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onDataChanged.invoke()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            onDataChanged.invoke()
        }
    })
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

const val SOFT_KEYBOARD_HEIGHT = 100

fun TextView.addOnKeyboardVisibilityListener(
    onKeyboardShown: () -> Unit,
    onKeyboardHidden: () -> Unit,
) {
    viewTreeObserver.addOnGlobalLayoutListener {
        if (rootView.isKeyboardShown()) {
            onKeyboardShown()
        } else {
            onKeyboardHidden()
        }
    }
}

fun View.isKeyboardShown(): Boolean =
    Rect().let { rect ->
        rootView.getWindowVisibleDisplayFrame(rect)
        rect
    }.let {
        rootView.bottom - it.bottom
    }.let { heightDiff ->
        heightDiff > SOFT_KEYBOARD_HEIGHT * rootView.resources.displayMetrics.density
    }

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int
): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
    val textColor = typedArray.getColor(0, 0)
    typedArray.recycle()
    return textColor
}

fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    block: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block(this.value, liveData.value)
    }
    return result
}