package at.appmonkey.amcalendarsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import at.appmonkey.amcalendar.base.AMCalendar
import at.appmonkey.amcalendarsample.databinding.ActivityMainBinding
import at.appmonkey.amcalendar.interfaces.RangeSelectionListener
import at.appmonkey.amcalendar.interfaces.SingleSelectionListener
import at.appmonkey.amcalendar.prepared.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            AMCalendar.singleSelect(this, object : SingleSelectionListener {
                override fun onSingleSelect(cal: Calendar?) {
                    if(cal != null) {
                        Toast.makeText(this@MainActivity, "Selected single: " + cal.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).show()
        }
        binding.button2.setOnClickListener {
            AMCalendar.rangeSelect(this, object : RangeSelectionListener {
                override fun onRangeSelect(cal1: Calendar?, cal2: Calendar?) {
                    if(cal1 != null && cal2 != null) {
                        Toast.makeText(this@MainActivity, "Selected range: " + cal1.time.toString() + " - " + cal2.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).show()
        }
        binding.button3.setOnClickListener {
            AMCalendar.singleSelect(this, object : SingleSelectionListener {
                override fun onSingleSelect(cal: Calendar?) {
                    if(cal != null) {
                        Toast.makeText(this@MainActivity, "Selected single: " + cal.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).shortcuts(TodaySingleShortcut(this), TomorrowSingleShortcut(this), YesterdaySingleShortcut(this))
            .show()
        }
        binding.button4.setOnClickListener {
            AMCalendar.rangeSelect(this, object : RangeSelectionListener {
                override fun onRangeSelect(cal1: Calendar?, cal2: Calendar?) {
                    if(cal1 != null && cal2 != null) {
                        Toast.makeText(this@MainActivity, "Selected range: " + cal1.time.toString() + " - " + cal2.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).shortcuts(TodayRangeShortcut(this), LastWeekRangeShortcut(this), LastMonthRangeShortcut(this))
            .show()
        }
        binding.button5.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, 1)
            AMCalendar.singleSelect(this, object : SingleSelectionListener {
                override fun onSingleSelect(cal: Calendar?) {
                    if(cal != null) {
                        Toast.makeText(this@MainActivity, "Selected single: " + cal.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).preselect(cal)
                .show()
        }
        binding.button6.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, 1)
            val calEnd = Calendar.getInstance()
            calEnd.set(Calendar.DAY_OF_MONTH, 4)
            AMCalendar.rangeSelect(this, object : RangeSelectionListener {
                override fun onRangeSelect(cal1: Calendar?, cal2: Calendar?) {
                    if(cal1 != null && cal2 != null) {
                        Toast.makeText(this@MainActivity, "Selected range: " + cal1.time.toString() + " - " + cal2.time.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }).preselect(cal, calEnd)
                .show()
        }
    }

}