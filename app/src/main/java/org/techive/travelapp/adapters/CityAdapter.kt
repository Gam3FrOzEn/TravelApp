package org.techive.travelapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_city.view.*
import org.techive.travelapp.R
import org.techive.travelapp.database.DBHelper
import org.techive.travelapp.fragments.LandmarkFragment
import org.techive.travelapp.models.City

class CityAdapter(
    var mValues: MutableList<City>,
    val mCtx: Context
) : RecyclerView.Adapter<CityAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_city, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(h: MyViewHolder, position: Int) {
        val m = mValues[position]

        val db = DBHelper(mCtx)
        h.name.text = m.name!!
        h.desc.text = m.description!!

        h.itemView.setOnClickListener {
            (mCtx as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, LandmarkFragment.newInstance(m)).addToBackStack(null)
                .commit()
        }
        h.itemView.setOnLongClickListener {
            if (db.checkIfLandmarkExist(m.id.toString()) > 0) {
                Toast.makeText(mCtx, "${m.name} has landmarks", Toast.LENGTH_LONG).show()
            } else {
                AlertDialog.Builder(mCtx)
                    .setTitle("Delete ${m.name}?")
                    .setMessage("Are you sure you want to delete ${m.name}?")
                    .setPositiveButton(
                        "Delete"
                    ) { p0, p1 ->
                        db.deleteCity(m.id.toString())
                        mValues.remove(m)
                        mValues = db.cities
                        Toast.makeText(mCtx, "Successfully deleted!", Toast.LENGTH_LONG).show()
                        notifyDataSetChanged()
                    }
                    .setNegativeButton(
                        "Dismiss"
                    ) { p0, p1 -> p0.dismiss() }
                    .show()
            }
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val name = itemView.name
        val desc = itemView.desc
    }

    companion object {
        private var onItemClickListener: OnItemClickListener? = null
    }

    fun setOnItemClickListener(click: OnItemClickListener) {
        onItemClickListener = click
    }

    interface OnItemClickListener {
        fun onItemClick(u: String)
    }

    fun setFilter(newList: MutableList<City>) {
        mValues = ArrayList()
        mValues.addAll(newList)
        notifyDataSetChanged()
    }
}
