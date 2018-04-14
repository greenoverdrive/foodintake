package com.overdrive.foodintake

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.overdrive.foodintake.signin.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat




class MainActivity : AppCompatActivity() {

    companion object {
        const val MILLIS_PER_DAY = 86400000
        const val PREFS_NAME = "userPrefs"
        const val PREF_USERNAME = "username"
        const val PREF_TOKEN = "token"

        const val REQUEST_LOGIN = 3
    }


    var username: String? = null
    var token: String? = null

    lateinit var breakfastText: TextView
    lateinit var lunchText: TextView
    lateinit var dinnerText: TextView

    lateinit var breakfastList: ListView
    lateinit var lunchList: ListView
    lateinit var dinnerList: ListView

    lateinit var dateText: TextView
    lateinit var leftButton: Button
    lateinit var rightButton: Button

    var presetDate = System.currentTimeMillis()
    var timeCounter = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)


        val sharedPreferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this)
        username = sharedPreferences?.getString(PREF_USERNAME, null)
        token = sharedPreferences?.getString(PREF_TOKEN, null)

        if (username == null || token == null ) {
            startActivityForResult(Intent(this, LoginActivity::class.java), MainActivity.REQUEST_LOGIN)
        }



        breakfastText = findViewById<TextView>(R.id.main_breakText) as TextView
        lunchText = findViewById<TextView>(R.id.main_lunchText) as TextView
        dinnerText = findViewById<TextView>(R.id.main_dinnerText) as TextView

        breakfastList = findViewById<ListView>(R.id.main_breakList) as ListView
        lunchList = findViewById<ListView>(R.id.main_lunchList) as ListView
        dinnerList = findViewById<ListView>(R.id.main_dinnerList) as ListView

        breakfastText.text = "Data not found"

        lunchText.visibility = View.INVISIBLE
        dinnerText.visibility = View.INVISIBLE
        breakfastList.visibility = View.INVISIBLE
        lunchList.visibility = View.INVISIBLE
        dinnerList.visibility = View.INVISIBLE




        dateText = findViewById<TextView>(R.id.main_date) as TextView
        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        with(dateText) {
            keyListener = null
            text = dateFormat.format(presetDate)
            setOnClickListener {
                presetDate = System.currentTimeMillis()
                text = dateFormat.format(presetDate)
                timeCounter = 0
                leftButton.visibility = View.INVISIBLE
            }
        }

        leftButton = findViewById<Button>(R.id.main_leftButton) as Button
        leftButton.setOnClickListener {
            presetDate += MainActivity.MILLIS_PER_DAY
            dateText.text = dateFormat.format(presetDate)
            if (--timeCounter == 0) {
                leftButton.visibility = View.INVISIBLE
            }
        }
        leftButton.visibility = View.INVISIBLE

        rightButton = findViewById<Button>(R.id.main_rightButton) as Button
        rightButton.setOnClickListener {
            presetDate -= MainActivity.MILLIS_PER_DAY
            dateText.text = dateFormat.format(presetDate)
            leftButton.visibility = View.VISIBLE
            timeCounter++
        }

        //TODO: refresh from server



        main_fab.setOnClickListener {
            startActivity(Intent(this, EnterMealsActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.action_exit) {
            val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
            editor.putString(PREF_USERNAME, null)
            editor.putString(PREF_TOKEN, null)
            editor.apply()

            startActivityForResult(Intent(this, LoginActivity::class.java), MainActivity.REQUEST_LOGIN)
        }

        return when (item.itemId) {
            R.id.action_exit -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == MainActivity.REQUEST_LOGIN) {
            username = data.extras.getString(MainActivity.PREF_USERNAME)
            token = data.extras.getString(MainActivity.PREF_TOKEN)

            val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
            editor.putString(PREF_USERNAME, username)
            editor.putString(PREF_TOKEN, token)
            editor.apply()
        }
    }


}
