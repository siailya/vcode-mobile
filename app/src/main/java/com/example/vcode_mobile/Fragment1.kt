package com.example.vcode_mobile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}