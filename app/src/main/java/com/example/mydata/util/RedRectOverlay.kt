package uz.ssd.egaz.ui.scanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.bobekos.bobek.scanner.overlay.BarcodeOverlay

class RedRectOverlay @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), BarcodeOverlay {

    private lateinit var rect: Rect

    private val paint by lazy {
        Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                3f,
                context?.resources?.displayMetrics
            )
        }
    }

    init {
        setWillNotDraw(false)
    }

    override fun onUpdate(posRect: Rect, barcodeValue: String) {
        rect = posRect
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (::rect.isInitialized) {
            canvas?.drawRect(rect, paint)
        }
    }
}