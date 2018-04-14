package com.overdrive.foodintake.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.overdrive.foodintake.R


/**
 * A simple [Fragment] subclass.
 */
class SnackFragment : ListFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_snack, container, false)
    }

    val foodNames = arrayOf("1. Chips 'Lays', 200g")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1, foodNames)
        listAdapter = adapter
    }

}// Required empty public constructor
