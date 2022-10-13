package at.appmonkey.amcalendar.prepared

import android.content.Context
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.base.AMCalendarRangeShortcut
import java.util.*

class LastWeekRangeShortcut(context: Context): AMCalendarRangeShortcut() {

    init {
        cal1 = Calendar.getInstance()
        cal1?.firstDayOfWeek = Calendar.MONDAY
        cal1?.add(Calendar.WEEK_OF_YEAR, -1)
        cal1?.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal2 = Calendar.getInstance()
        cal2?.firstDayOfWeek = Calendar.MONDAY
        cal2?.add(Calendar.WEEK_OF_YEAR, -1)
        cal2?.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        text = context.resources.getString(R.string.amcalendar_template_last_week)
    }

}