package ru.bashcony.kinosearch.infra.utils

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.text.format.DateFormat
import android.util.TypedValue
import android.widget.TextView
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