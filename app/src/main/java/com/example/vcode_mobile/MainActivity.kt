package com.example.vcode_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun getMonthText(monthNumber: Int?): String {
        when (monthNumber) {
            1 -> {
                return "Январь"
            }
            2 -> {
                return "Февраль"
            }
            3 -> {
                return "Март"
            }
            4 -> {
                return "Апрель"
            }
            5 -> {
                return "Май"
            }
            6 -> {
                return "Июнь"
            }
            7 -> {
                return "Июль"
            }
            8 -> {
                return "Август"
            }
            9 -> {
                return "Сентябрь"
            }
            10 -> {
                return "Октябрь"
            }
            11 -> {
                return "Ноябрь"
            }
            12 -> {
                return "Декабрь"
            }
            else -> {
                return ""
            }
        }
    }

    private var currentMonth: Int? = getCurrentDateTime().toString("MM").toInt()

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return Fragment1()
            }
            1 -> {
                return Fragment2()
            }
            2 -> {
                return Fragment3()
            }
            else -> {
                return Fragment1()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return getMonthText(this.currentMonth)
            }
            1 -> {
                return getMonthText(this.currentMonth?.plus(1))
            }
            2 -> {
                return getMonthText(this.currentMonth?.plus(2))
            }
        }
        return super.getPageTitle(position)
    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }
}