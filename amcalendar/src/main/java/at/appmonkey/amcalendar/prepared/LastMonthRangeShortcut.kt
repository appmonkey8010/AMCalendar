package at.appmonkey.amcalendar.prepared

import android.content.Context
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.base.AMCalendarRangeShortcut
import java.util.*

class LastMonthRangeShortcut(context: Context): AMCalendarRangeShortcut() {

    init {
        cal1 = Calendar.getInstance()
        cal1?.add(Calendar.MONTH, -1)
        val minDayOfMonth = cal1?.getActualMinimum(Calendar.DAY_OF_MONTH)
        if(minDayOfMonth != null) {
            cal1?.set(Calendar.DAY_OF_MONTH, minDayOfMonth)
        }
        cal2 = Calendar.getInstance()
        cal2?.add(Calendar.MONTH, -1)
        val maxDayOfMonth = cal2?.getActualMaximum(Calendar.DAY_OF_MONTH)
        if(maxDayOfMonth != null) {
            cal2?.set(Calendar.DAY_OF_MONTH, maxDayOfMonth)
        }
        text = context.resources.getString(R.string.amcalendar_template_last_month)
    }

}