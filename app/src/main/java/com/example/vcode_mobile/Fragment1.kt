package com.example.vcode_mobile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
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

        val response: Array<Array<Pair<Boolean, Int>>> = Array(5)
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
    private var calendar: Any? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons = ArrayList<Button>()
        for (i in 0..34) {
            val idString = "@+id/button$i"
            val buttonID = view.resources.getIdentifier(idString, "id", "fragment1")
            buttons.add(view.findViewById<Button>(buttonID))
//            buttons[i].setOnClickListener(buttonclicked)
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