package at.appmonkey.amcalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.elements.MonthElement
import at.appmonkey.amcalendar.enums.CalendarMode
import at.appmonkey.amcalendar.interfaces.RangeSelectionListener
import at.appmonkey.amcalendar.interfaces.SingleSelectionListener
import at.appmonkey.amcalendar.ui.DayView
import at.appmonkey.amcalendar.ui.MonthView
import java.util.*

class MonthAdapter(private val dataSet: ArrayList<MonthElement>) :
    RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    var calendarMode = CalendarMode.SINGLE
    var calSelection: Calendar? = null
    var calSelectionEnd: Calendar? = null
    var singleSelectionListener: SingleSelectionListener? = null
    var rangeSelectionListener: RangeSelectionListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monthView: MonthView = view.findViewById(R.id.month_view)
        private val layout1: LinearLayout = view.findViewById(R.id.layout_1)
        private val layout2: LinearLayout = view.findViewById(R.id.layout_2)
        private val layout3: LinearLayout = view.findViewById(R.id.layout_3)
        private val layout4: LinearLayout = view.findViewById(R.id.layout_4)
        val layout5: LinearLayout = view.findViewById(R.id.layout_5)
        val layout6: LinearLayout = view.findViewById(R.id.layout_6)
        val dayViews: ArrayList<DayView> = arrayListOf()

        init {
            dayViews.clear()
            for (i in 0 until layout1.childCount step 1) {
                val elementView = layout1.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
            for (i in 0 until layout2.childCount step 1) {
                val elementView = layout2.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
            for (i in 0 until layout3.childCount step 1) {
                val elementView = layout3.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
            for (i in 0 until layout4.childCount step 1) {
                val elementView = layout4.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
            for (i in 0 until layout5.childCount step 1) {
                val elementView = layout5.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
            for (i in 0 until layout6.childCount step 1) {
                val elementView = layout6.getChildAt(i)
                if(elementView is DayView) {
                    applyWeekday(i, elementView)
                    dayViews.add(elementView)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_month, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monthElement = dataSet[position]
        holder.monthView.setMonthElement(monthElement)

        val calReference = Calendar.getInstance()
        calReference.set(Calendar.YEAR, monthElement.year)
        calReference.set(Calendar.MONTH, monthElement.month)
        calReference.set(Calendar.DAY_OF_MONTH, 1)

        val weekdayReference = calReference.get(Calendar.DAY_OF_WEEK)
        val lastDayOfMonth = calReference.getActualMaximum(Calendar.DAY_OF_MONTH)

        for(dayView in holder.dayViews) {
            dayView.monthAdapter = this
            dayView.setOnClickListener { viewClick ->
                val inUse = viewClick.getTag(R.id.tag_amcalendar_in_use) as Boolean
                val dayOfMonth = viewClick.getTag(R.id.tag_amcalendar_day) as Int
                val month = viewClick.getTag(R.id.tag_amcalendar_month) as Int
                val year = viewClick.getTag(R.id.tag_amcalendar_year) as Int
                if(inUse) {
                    val calClick = Calendar.getInstance()
                    calClick.set(Calendar.YEAR, year)
                    calClick.set(Calendar.MONTH, month)
                    calClick.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    removeTime(calClick)
                    if(calendarMode == CalendarMode.SINGLE) {
                        calSelection = calClick
                        notifyDataSetChanged()
                        singleSelectionListener?.onSingleSelect(calSelection)
                    }
                    else if(calendarMode == CalendarMode.RANGE) {
                        if(calSelection == null) {
                            calSelection = calClick.clone() as Calendar
                        }
                        else if(calSelectionEnd == null) {
                            val calSelectionCurrent = calSelection
                            if(calSelectionCurrent != null && !isSameDay(calClick, calSelectionCurrent)) {
                                if(calClick.timeInMillis < calSelectionCurrent.timeInMillis) {
                                    calSelectionEnd = calSelectionCurrent.clone() as Calendar
                                    calSelection = calClick.clone() as Calendar
                                }
                                else {
                                    calSelectionEnd = calClick.clone() as Calendar
                                }
                            }
                        }
                        else {
                            val calSelectionCurrent = calSelection
                            val calSelectionEndCurrent = calSelectionEnd
                            if(calSelectionCurrent != null && calSelectionEndCurrent != null) {
                                if(isSameDay(calClick, calSelectionCurrent)) {
                                    calSelectionEnd = null
                                }
                                else if(isSameDay(calClick, calSelectionEndCurrent)) {
                                    calSelection = calClick.clone() as Calendar
                                    calSelectionEnd = null
                                }
                                else if(calClick.timeInMillis > calSelectionCurrent.timeInMillis) {
                                    calSelectionEnd = calClick.clone() as Calendar
                                }
                                else if(calClick.timeInMillis < calSelectionCurrent.timeInMillis) {
                                    calSelection = calClick.clone() as Calendar
                                }
                            }
                        }
                        notifyDataSetChanged()
                        rangeSelectionListener?.onRangeSelect(calSelection, calSelectionEnd)
                    }
                }
            }
        }

        for(i in 0 until 7 step 1) {
            val dayView = holder.dayViews[i]
            val weekday = dayView.getTag(R.id.tag_amcalendar_weekday) as Int
            if(weekday == weekdayReference) {
                for(j in (i + lastDayOfMonth) until 42 step 1) {
                    holder.dayViews[j].isInUse = false
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_in_use, false)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_day, 999)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_month, monthElement.month)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_year, monthElement.year)
                }
                var firstDay = 1
                for(j in i until (i + lastDayOfMonth) step 1) {
                    holder.dayViews[j].isInUse = true
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_in_use, true)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_day, firstDay)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_month, monthElement.month)
                    holder.dayViews[j].setTag(R.id.tag_amcalendar_year, monthElement.year)
                    firstDay++
                }
                break
            }
            else {
                holder.dayViews[i].isInUse = false
                holder.dayViews[i].setTag(R.id.tag_amcalendar_in_use, false)
                holder.dayViews[i].setTag(R.id.tag_amcalendar_day, 0)
                holder.dayViews[i].setTag(R.id.tag_amcalendar_month, monthElement.month)
                holder.dayViews[i].setTag(R.id.tag_amcalendar_year, monthElement.year)
            }
        }
        if(!holder.dayViews[28].isInUse) {
            if(holder.layout5.visibility != View.GONE) {
                holder.layout5.visibility = View.GONE
            }
        }
        else {
            if(holder.layout5.visibility != View.VISIBLE) {
                holder.layout5.visibility = View.VISIBLE
            }
        }
        if(!holder.dayViews[35].isInUse) {
            if(holder.layout6.visibility != View.GONE) {
                holder.layout6.visibility = View.GONE
            }
        }
        else {
            if(holder.layout6.visibility != View.VISIBLE) {
                holder.layout6.visibility = View.VISIBLE
            }
        }

        for(dayView in holder.dayViews) {
            dayView.invalidate()
        }
    }

    override fun getItemCount() = dataSet.size

    fun getPositionOfElement(month: Int, year: Int): Int {
        for (i in 0 until dataSet.size step 1) {
            if(dataSet[i].month == month && dataSet[i].year == year) {
                return i
            }
        }
        return -1
    }

    private fun applyWeekday(i: Int, dayView: DayView) {
        when(i) {
            0 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.MONDAY)
            1 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.TUESDAY)
            3 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.WEDNESDAY)
            4 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.THURSDAY)
            6 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.FRIDAY)
            7 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.SATURDAY)
            9 -> dayView.setTag(R.id.tag_amcalendar_weekday, Calendar.SUNDAY)
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

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
            return true
        }
        return false
    }

}
