package com.overdrive.foodintake

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.overdrive.foodintake.utils.PagerAdapter
import kotlinx.android.synthetic.main.activity_enter_meals.*


class EnterMealsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_SEARCH = 4
    }

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_meals)
        setSupportActionBar(meals_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        tabLayout = findViewById<View>(R.id.meals_tablayout) as TabLayout

        with(tabLayout) {
            addTab(tabLayout.newTab().setText(R.string.meals_tab_break))
            addTab(tabLayout.newTab().setText(R.string.meals_tab_lunch))
            addTab(tabLayout.newTab().setText(R.string.meals_tab_dinner))
            addTab(tabLayout.newTab().setText(R.string.meals_tab_snack))

            // Set the tabs to fill the entire layout.
            tabGravity = TabLayout.GRAVITY_FILL
        }


        viewPager = findViewById<View>(R.id.meals_viewpager) as ViewPager

        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })


        bottomNavigationView = findViewById<BottomNavigationView>(R.id.meals_bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        when (item.getItemId()) {
                            R.id.meals_menu_action_search -> startActivityForResult(
                                    Intent(this@EnterMealsActivity, SearchActivity::class.java),
                                    EnterMealsActivity.REQUEST_SEARCH)


                        }
                        return true
                    }
                })


    }

}





