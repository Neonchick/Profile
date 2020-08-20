package ru.skillbranch.profile.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.skillbranch.profile.R

class AspectRatioImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) :
        AppCompatImageView(context, attrs, defStyleAttr)
{
    companion object
    {
        private const val DEFAULT_ASPECT_RATIO = 1.78f
    }

    private var aspectRatic = DEFAULT_ASPECT_RATIO

    init
    {
        if (attrs != null)
        {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            aspectRatic = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio,
                                     DEFAULT_ASPECT_RATIO)
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val newHeigth = (measuredWidth/aspectRatic).toInt()
        setMeasuredDimension(measuredWidth,newHeigth)
    }
}