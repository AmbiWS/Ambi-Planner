package com.ambiws.ambiplanner.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.databinding.ViewToolbarBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding: ViewToolbarBinding
    val ivLeftAction: ImageView

    init {
        binding = ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        with(binding) {
            // Title
            tvTitle.text = customAttrs.getString(R.styleable.CustomToolbar_title) ?: ""
            // Action left
            ivLeftAction = binding.actionLeft
            if (customAttrs.getBoolean(R.styleable.CustomToolbar_leftActionVisible, true)) {
                actionLeft.visibility = View.VISIBLE
                actionLeft.setImageDrawable(
                    customAttrs.getDrawable(R.styleable.CustomToolbar_leftActionSrc)
                        ?: AppCompatResources.getDrawable(context, R.drawable.ic_arrow_left)
                )
            } else {
                actionLeft.visibility = View.INVISIBLE
            }
            // Action right
            customAttrs.getDrawable(R.styleable.CustomToolbar_rightActionSrc)
                ?.let { rightActionDrawable ->
                    actionRight.setImageDrawable(rightActionDrawable)
                    actionRight.visibility = View.VISIBLE
                }
        }
        customAttrs.recycle()
    }
}
