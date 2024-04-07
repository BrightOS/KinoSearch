package ru.bashcony.kinosearch.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import ru.bashcony.kinosearch.R


class ExpandableTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    var originalText: CharSequence? = null
        private set
    private var trimmedText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var trim = true
    private var trimLength: Int

    constructor(context: Context) : this(context, null)

    init {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        this.trimLength =
            typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH)
        typedArray.recycle()

        setOnClickListener {
            trim = !trim
            setText()
            requestFocusFromTouch()
        }
    }

    private fun setText() {
        super.setText(displayableText, bufferType)
    }

    private val displayableText: CharSequence?
        get() = if (trim) trimmedText else originalText

    override fun setText(text: CharSequence, type: BufferType) {
        originalText = text
        trimmedText = getTrimmedText()
        bufferType = type
        setText()
    }

    private fun getTrimmedText(): CharSequence? {
        return if (originalText != null && originalText!!.length > trimLength) {
            SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS)
        } else {
            originalText
        }
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = trimLength
        trimmedText = getTrimmedText()
        setText()
    }

    fun getTrimLength(): Int {
        return trimLength
    }

    companion object {
        private const val DEFAULT_TRIM_LENGTH = 50
        private const val ELLIPSIS = "....."
    }
}