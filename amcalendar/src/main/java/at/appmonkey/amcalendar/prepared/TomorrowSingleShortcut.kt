package at.appmonkey.amcalendar.prepared

import android.content.Context
import at.appmonkey.amcalendar.R
import at.appmonkey.amcalendar.base.AMCalendarSingleShortcut
import java.util.*

class TomorrowSingleShortcut(context: Context): AMCalendarSingleShortcut() {

    init {
        cal = Calendar.getInstance()
        cal?.add(Calendar.DAY_OF_MONTH, 1)
        text = context.resources.getString(R.string.amcalendar_template_tomorrow)
    }

}