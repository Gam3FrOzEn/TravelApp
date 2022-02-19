package org.techive.travelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_city.view.*
import org.techive.travelapp.R
import org.techive.travelapp.adapters.CityAdapter
import org.techive.travelapp.database.DBHelper
import org.techive.travelapp.models.City

class CityFragment : Fragment() {
    var list: MutableList<City> = ArrayList()
    var recyclerView: RecyclerView? = null
    var mAdapter: CityAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.cities)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        recyclerView = view.recycler_view

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    view.add.hide()
                } else {
                    view.add.show()
                }
            }
        })

        view.add.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, AddCityFragment())
                .addToBackStack(null).commit()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        list = DBHelper(requireContext()).cities
        mAdapter = CityAdapter(list, requireContext())
        recyclerView!!.adapter = mAdapter
    }
}