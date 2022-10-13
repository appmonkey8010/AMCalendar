package at.appmonkey.amcalendar.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import at.appmonkey.amcalendar.enums.CalendarMode
import at.appmonkey.amcalendar.fragments.CalendarFragment
import at.appmonkey.amcalendar.interfaces.RangeSelectionListener
import at.appmonkey.amcalendar.interfaces.SingleSelectionListener
import java.util.Calendar

class AMCalendar {

    companion object {

        fun singleSelect(activity: FragmentActivity,
                         singleSelectionListener: SingleSelectionListener): SingleAMCalendar {
            return SingleAMCalendar(activity.supportFragmentManager, singleSelectionListener)
        }

        fun rangeSelect(activity: FragmentActivity,
                        rangeSelectionListener: RangeSelectionListener) : RangeAMCalendar {
            return RangeAMCalendar(activity.supportFragmentManager, rangeSelectionListener)
        }

    }

    class SingleAMCalendar(private val fragmentManager: FragmentManager,
                           singleSelectionListener: SingleSelectionListener) {

        private var singleSelectionListener: SingleSelectionListener? = null

        init {
            this.singleSelectionListener = singleSelectionListener
        }

        private var shortcuts: Array<out AMCalendarShortcut>? = null
        private var cal: Calendar? = null

        fun shortcuts(vararg shortcuts: AMCalendarSingleShortcut): SingleAMCalendar {
            this.shortcuts = shortcuts
            return this
        }

        fun preselect(cal: Calendar): SingleAMCalendar {
            this.cal = cal
            return this
        }

        fun show() {
            val calendarFragment = CalendarFragment()
            calendarFragment.singleSelectionListener = singleSelectionListener
            calendarFragment.rangeSelectionListener = null
            val bundle = Bundle()
            bundle.putString("calendarType", CalendarMode.SINGLE.toString())
            bundle.putSerializable("shortcuts", shortcuts)
            if(cal != null) {
                bundle.putSerializable("cal", cal)
            }
            calendarFragment.arguments = bundle
            calendarFragment.show(fragmentManager, "AMCalendar")
        }

    }

    class RangeAMCalendar(private val fragmentManager: FragmentManager,
                          rangeSelectionListener: RangeSelectionListener) {

        private var rangeSelectionListener: RangeSelectionListener? = null

        init {
            this.rangeSelectionListener = rangeSelectionListener
        }

        private var shortcuts: Array<out AMCalendarShortcut>? = null
        private var cal: Calendar? = null
        private var calEnd: Calendar? = null

        fun shortcuts(vararg shortcuts: AMCalendarRangeShortcut): RangeAMCalendar {
            this.shortcuts = shortcuts
            return this
        }

        fun preselect(cal: Calendar, calEnd: Calendar): RangeAMCalendar {
            this.cal = cal
            this.calEnd = calEnd
            return this
        }

        fun show() {
            val calendarFragment = CalendarFragment()
            calendarFragment.singleSelectionListener = null
            calendarFragment.rangeSelectionListener = rangeSelectionListener
            val bundle = Bundle()
            bundle.putString("calendarType", CalendarMode.RANGE.toString())
            bundle.putSerializable("shortcuts", shortcuts)
            if(cal != null) {
                bundle.putSerializable("cal", cal?.clone() as Calendar)
            }
            if(calEnd != null) {
                bundle.putSerializable("calEnd", calEnd?.clone() as Calendar)
            }
            calendarFragment.arguments = bundle
            calendarFragment.show(fragmentManager, "AMCalendar")
        }

    }

}