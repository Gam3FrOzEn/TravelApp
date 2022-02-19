package org.techive.travelapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.techive.travelapp.R
import org.techive.travelapp.fragments.CityFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.main_layout, CityFragment()).commit()
    }
}