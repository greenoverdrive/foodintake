package com.overdrive.foodintake.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.overdrive.foodintake.R


/**
 * A simple [Fragment] subclass.
 */
class DinnerFragment : ListFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_dinner, container, false)
    }

    //val catNames = arrayOf("Рыжик", "Барсик", "Мурзик", "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка", "Кузя", "Китти", "Масяня", "Симба")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //val adapter = ArrayAdapter(activity,
         //       android.R.layout.simple_list_item_1, catNames)
        //listAdapter = adapter
    }

}// Required empty public constructor
