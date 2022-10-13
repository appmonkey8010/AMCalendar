package at.appmonkey.amcalendar.prepared

import android.content.Context
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.base.AMCalendarRangeShortcut
import java.util.*

class TodayRangeShortcut(context: Context): AMCalendarRangeShortcut() {

    init {
        cal1 = Calendar.getInstance()
        cal2 = null
        text = context.resources.getString(R.string.amcalendar_template_today)
    }

}