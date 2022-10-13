package at.appmonkey.amcalendar.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.adapter.MonthAdapter
import java.util.*

class DayView : View {

    private var paintText = Paint()
    private var paintTextSelected = Paint()
    private var paintSelection = Paint()
    private var paintSelectionTrack = Paint()

    private var dayOfMonth = 0
    private var month = 0
    private var year = 0

    private var dayOfMonthSelected: Int? = null
    private var monthSelected: Int? = null
    private var yearSelected: Int? = null

    private var dayOfMonthSelectedEnd: Int? = null
    private var monthSelectedEnd: Int? = null
    private var yearSelectedEnd: Int? = null

    var isInUse = false

    var monthAdapter: MonthAdapter? = null

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        init()
    }

    private fun init() {
        paintText.color = ContextCompat.getColor(context, R.color.colorAMCalendarDay)
        paintText.textAlign = Paint.Align.CENTER
        paintText.flags = Paint.ANTI_ALIAS_FLAG

        paintTextSelected.color = ContextCompat.getColor(context, R.color.colorAMCalendarDaySelected)
        paintTextSelected.textAlign = Paint.Align.CENTER
        paintTextSelected.flags = Paint.ANTI_ALIAS_FLAG

        paintSelection.color = ContextCompat.getColor(context, R.color.colorAMCalendarDaySelectedCircle)
        paintSelection.style = Paint.Style.FILL
        paintSelection.flags = Paint.ANTI_ALIAS_FLAG

        paintSelectionTrack.color = ContextCompat.getColor(context, R.color.colorAMCalendarDaySelectedCircle)
        paintSelectionTrack.style = Paint.Style.FILL
        paintSelectionTrack.flags = Paint.ANTI_ALIAS_FLAG
        paintSelectionTrack.alpha = 100
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas != null) {
            if(getTag(R.id.tag_amcalendar_day) != null) {
                dayOfMonth = getTag(R.id.tag_amcalendar_day) as Int
                month = getTag(R.id.tag_amcalendar_month) as Int
                year = getTag(R.id.tag_amcalendar_year) as Int
                if(monthAdapter != null && monthAdapter?.calSelection != null) {
                    dayOfMonthSelected = monthAdapter?.calSelection?.get(Calendar.DAY_OF_MONTH)
                    monthSelected = monthAdapter?.calSelection?.get(Calendar.MONTH)
                    yearSelected = monthAdapter?.calSelection?.get(Calendar.YEAR)
                }
                else {
                    dayOfMonthSelected = null
                    monthSelected = null
                    yearSelected = null
                }
                if(monthAdapter != null && monthAdapter?.calSelectionEnd != null) {
                    dayOfMonthSelectedEnd = monthAdapter?.calSelectionEnd?.get(Calendar.DAY_OF_MONTH)
                    monthSelectedEnd = monthAdapter?.calSelectionEnd?.get(Calendar.MONTH)
                    yearSelectedEnd = monthAdapter?.calSelectionEnd?.get(Calendar.YEAR)
                }
                else {
                    dayOfMonthSelectedEnd = null
                    monthSelectedEnd = null
                    yearSelectedEnd = null
                }
            }
            if(isInUse) {
                drawDay(canvas)
            }
            else {
                drawAlt(canvas)
            }
        }
    }

    private fun drawDay(canvas: Canvas) {
        val centerX = (width/2).toFloat()
        val centerY = (height/2).toFloat()
        val textSize = (canvas.height/2.5f)

        paintText.textSize = textSize
        paintTextSelected.textSize = textSize
        var selected = false
        if(dayOfMonth == dayOfMonthSelected && month == monthSelected && year == yearSelected) {
            if(dayOfMonthSelectedEnd != null)  {
                canvas.drawRect(centerX, centerY - (canvas.height/2.6f), width.toFloat(), centerY + (canvas.height/2.6f), paintSelectionTrack)
            }
            canvas.drawCircle(centerX, centerY, (canvas.height/2.2f), paintSelection)
            selected = true
        }
        if(dayOfMonth == dayOfMonthSelectedEnd && month == monthSelectedEnd && year == yearSelectedEnd) {
            canvas.drawRect(0F, centerY - (canvas.height/2.6f), centerX, centerY + (canvas.height/2.6f), paintSelectionTrack)
            canvas.drawCircle(centerX, centerY, (canvas.height/2.2f), paintSelection)
            selected = true
        }
        if(!selected && yearSelectedEnd != null) {
            val cal = Calendar.getInstance()
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            cal1.set(yearSelected!!, monthSelected!!, dayOfMonthSelected!!)
            cal2.set(yearSelectedEnd!!, monthSelectedEnd!!, dayOfMonthSelectedEnd!!)
            removeTime(cal, cal1, cal2)
            if(cal.timeInMillis > cal1.timeInMillis && cal.timeInMillis < cal2.timeInMillis) {
                canvas.drawRect(0F, centerY - (canvas.height/2.6f), width.toFloat(), centerY + (canvas.height/2.6f), paintSelectionTrack)
            }
        }
        canvas.drawText(dayOfMonth.toString(), centerX, centerY + (textSize * 0.35f), if(selected) paintTextSelected else paintText)
    }

    private fun drawAlt(canvas: Canvas) {
        if(yearSelectedEnd != null) {
            val centerY = (height/2).toFloat()
            val cal = Calendar.getInstance()
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.set(yearSelected!!, monthSelected!!, dayOfMonthSelected!!)
            cal2.set(yearSelectedEnd!!, monthSelectedEnd!!, dayOfMonthSelectedEnd!!)
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            if(dayOfMonth == 0) {
                cal.set(Calendar.DAY_OF_MONTH, 1)
            }
            else {
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            }
            val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
            val dayOfYear1 = cal1.get(Calendar.DAY_OF_YEAR)
            val dayOfYear2 = cal2.get(Calendar.DAY_OF_YEAR)
            val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
            removeTime(cal, cal1, cal2)
            if(cal.timeInMillis >= cal1.timeInMillis && cal.timeInMillis <= cal2.timeInMillis) {
                if(dayOfMonth == 1 && dayOfYear == dayOfYear1) {
                    return
                }
                else if(dayOfMonth != 1 && dayOfYear == dayOfYear2) {
                    return
                }
                else {
                    canvas.drawRect(0F, centerY - (canvas.height/2.6f), width.toFloat(),
                        centerY + (canvas.height/2.6f), paintSelectionTrack)
                }
            }
        }
    }

    private fun removeTime(vararg cals: Calendar) {
        for(cal in cals) {
            cal.set(Calendar.MILLISECOND, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.HOUR_OF_DAY, 0)
        }
    }

}