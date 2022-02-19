package org.techive.travelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_landmark.view.*
import org.techive.travelapp.R
import org.techive.travelapp.adapters.LandmarkAdapter
import org.techive.travelapp.database.DBHelper
import org.techive.travelapp.models.City
import org.techive.travelapp.models.Landmark

private const val ARG_PARAM1 = "param1"

class LandmarkFragment : Fragment() {
    private var city: City? = null
    var list: MutableList<Landmark> = ArrayList()
    var recyclerView: RecyclerView? = null
    var mAdapter: LandmarkAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getSerializable(ARG_PARAM1) as City
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landmark, container, false)
        (activity as AppCompatActivity).supportActionBar!!.title = "${city!!.name}'s landmarks"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        view.add_landmark.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, AddLandmarkFragment.newInstance(city!!))
                .addToBackStack("a").commit()
        }

        recyclerView = view.recycler_view
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    view.add_landmark.hide()
                } else {
                    view.add_landmark.show()
                }
            }
        })

        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        list = DBHelper(requireContext()).getLandmarks(city!!.id.toString())
        mAdapter = LandmarkAdapter(list, requireContext())
        recyclerView!!.adapter = mAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: City) =
            LandmarkFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}