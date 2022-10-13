package at.appmonkey.amcalendar.prepared

import android.content.Context
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.base.AMCalendarSingleShortcut
import java.util.*

class TodaySingleShortcut(context: Context): AMCalendarSingleShortcut() {

    init {
        cal = Calendar.getInstance()
        text = context.resources.getString(R.string.amcalendar_template_today)
    }

}