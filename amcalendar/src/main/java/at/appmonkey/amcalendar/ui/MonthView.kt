package at.appmonkey.amcalendar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.elements.MonthElement

class MonthView : LinearLayout {

    private lateinit var view: LinearLayout

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        init()
    }

    private fun init() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_view_month, this, true) as LinearLayout
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("SetTextI18n")
    fun setMonthElement(monthElement: MonthElement) {
        view.findViewById<TextView>(R.id.text_month).text = resources.getStringArray(
            R.array.amcalendar_months)[monthElement.month] + " " + monthElement.year
    }

}