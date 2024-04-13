package ru.bashcony.kinosearch.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.databinding.ItemEmptyBinding
import ru.bashcony.kinosearch.infra.utils.dp
import ru.bashcony.kinosearch.infra.utils.setVisible
import kotlin.math.roundToInt
import kotlin.reflect.cast


class EmptySupportRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {
    private var emptyView: View? = null

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val view = emptyView
        if (view != null && adapter?.itemCount == 0) {
            view.measure(widthSpec, heightSpec)
            setMeasuredDimension(
                view.measuredWidth,
                view.measuredHeight
            )
            return
        }

        super.onMeasure(widthSpec, heightSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        emptyView?.layout(l, t, r, b)
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        if (adapter?.itemCount == 0) {
            emptyView?.draw(c)
        }
    }

    /**
     * Текст, отображаемый после фразы:
     * "Нет информации о "...
     */
    var emptyText: String = ""
        set(value) {
            this.emptyView = ItemEmptyBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            ).apply {
                emptyTitle.text = "Нет информации о $value"
                emptyTitle.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_sad
                    ), null, null, null
                )
            }.root

            invalidate()

            field = value
        }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
//                emptyView?.setVisible(itemCount == 0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                emptyView?.setVisible(itemCount == 0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                emptyView?.setVisible(itemCount == 0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                emptyView?.setVisible(itemCount == 0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                emptyView?.setVisible(itemCount == 0)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                emptyView?.setVisible(itemCount == 0)
            }
        })
    }
}