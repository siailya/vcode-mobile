package com.example.vcode_mobile

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MONTH = "month"
private const val YEAR = "year"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCalendarData(year: Int, month: Int): Array<Array<Pair<Boolean, Int>>> {
        val initialDate: LocalDate = LocalDate.of(year, month, 1)

        var weekCount = 0
        val date = initialDate.minusDays((initialDate.dayOfWeek.value - 1).toLong())
        while (date.plusWeeks(weekCount.toLong()).monthValue <= initialDate.monthValue) weekCount++;

        val response: Array<Array<Pair<Boolean, Int>>> = Array(weekCount)
        { Array(7) { Pair(false, 0) } }

        var tmp: LocalDate = LocalDate.from(initialDate)
        for ((row, week) in response.withIndex())
            for ((column, _) in week.withIndex()) {
                if (row == 0 && initialDate.dayOfWeek.value > column + 1)
                    response[row][column] = Pair(
                        false,
                        initialDate.minusDays((initialDate.dayOfWeek.value - column - 1).toLong()).dayOfMonth
                    )
                else {
                    response[row][column] = Pair(tmp.month == initialDate.month, tmp.dayOfMonth)
                    tmp = tmp.plusDays(1)
                }
            }
        return response
    }

    private var month: Int = 0
    private var year: Int = 0
    private var calendar: Array<Array<Pair<Boolean, Int>>>? = null

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private var currentMonth: Int = getCurrentDateTime().toString("MM").toInt()
    private var currentYear: Int = getCurrentDateTime().toString("YYYY").toInt()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            month = it.getInt(MONTH)
            year = it.getInt(YEAR)
        }

        calendar = getCalendarData(currentYear, currentMonth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    private var periodStart: Any? = null
    private var periodEnd: Any? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons = ArrayList<Button>()
        val table: TableLayout = view.findViewById(R.id.buttonsTable)
        for (i in 1 until table.childCount) {
            val row: TableRow = table.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val b: Button = row.getChildAt(j) as Button
                buttons.add(b)
                b.isEnabled = calendar!![i - 1][j].first
                b.text = calendar!![i - 1][j].second.toString()

                b.setOnClickListener {
                    if (periodStart == null) {
                        periodStart = (b.text as String).toInt()
                        b.setBackgroundColor(Color.MAGENTA)
                    } else if (periodEnd == null) {
                        periodEnd = (b.text as String).toInt()

                        if ((periodEnd as Int) < (periodStart as Int)) {
                            periodStart = periodStart.also {periodStart = periodEnd}
                        }

                        b.setBackgroundColor(Color.MAGENTA)

                        for (btn in buttons) {
                            if ((btn.text as String).toInt() < (periodEnd as Int) &&
                                (btn.text as String).toInt() > (periodStart as Int)
                            ) {
                                btn.setBackgroundColor(Color.MAGENTA)
                            }
                        }
                    } else {
                        periodStart = null
                        periodEnd = null
                        for (btn in buttons) {
                            if (b.isEnabled) {
                                btn.setBackgroundColor(Color.rgb(98, 0, 238))
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        @JvmStatic
        fun newInstance(month: Int?, year: Int?) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    if (month != null) {
                        putInt(MONTH, month)
                    }
                    if (year != null) {
                        putInt(YEAR, year)
                    }
                }
            }
    }
}