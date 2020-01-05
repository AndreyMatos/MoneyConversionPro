package br.com.moneyconversionpro.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.moneyconversionpro.R

class IconButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var mButtonText: String? = ""
    var mTextColor: Int = 0
    var icon: Drawable? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.IconButton,
            0, 0
        ).apply {
            try {
                mButtonText = getString(R.styleable.IconButton_btnText)
                mTextColor = getColor(R.styleable.IconButton_btnTextColor, Color.BLACK)
                icon = getDrawable(R.styleable.IconButton_icon)
            } finally {
                recycle()
            }
        }

        inflate(context, R.layout.icon_button_layout, this)
        val btnTextView: TextView = findViewById(R.id.btnText)
        val btnIcon: ImageView = findViewById(R.id.icon)
        btnTextView.text = mButtonText
        btnTextView.setTextColor(mTextColor)
        btnIcon.setImageDrawable(icon)
    }
}