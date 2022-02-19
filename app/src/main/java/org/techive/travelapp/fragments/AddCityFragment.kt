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

class AddCityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.add_city)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
                    DBHelper(requireContext()).insertCity(City(n, d))
                    requireActivity().supportFragmentManager.popBackStack(
                        null,
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
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        return super.onOptionsItemSelected(item)
    }
}