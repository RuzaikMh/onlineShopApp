package ruzaik.mh.onlineshopapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText(context: Context, attrs : AttributeSet) : AppCompatEditText(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont() {

        // this is used to get the file from the assets folder and set it to the title textview
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}