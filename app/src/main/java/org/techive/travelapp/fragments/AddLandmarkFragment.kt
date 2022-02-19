package org.techive.travelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_add.view.*
import org.techive.travelapp.R
import org.techive.travelapp.database.DBHelper
import org.techive.travelapp.models.City
import org.techive.travelapp.models.Landmark

private const val ARG_PARAM1 = "param1"

class AddLandmarkFragment : Fragment() {
    private var city: City? = null

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.add_landmark)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        view.save.text = getString(R.string.add_landmark)

        view.save.setOnClickListener {
            val n = view.n.text.toString()
            val d = view.d.text.toString()

            when {
                n.isEmpty() -> {
                    view.n.error = getString(R.string.enter_name)
                }
                d.isEmpty() -> {
                    view.d.error = getString(R.string.enter_description)
                }
                else -> {
                    DBHelper(requireContext()).insertLandmark(Landmark(n, d, city!!.id.toString()))
                    requireActivity().supportFragmentManager.popBackStack(
                        "a",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().supportFragmentManager.popBackStack(
                "a",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: City) =
            AddLandmarkFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}