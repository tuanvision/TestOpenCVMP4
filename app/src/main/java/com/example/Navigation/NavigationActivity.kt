package com.example.Navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.testopencv.R
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        im_hamburger?.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

    }

    fun closeDrawer(item: SideNavItem) {

        drawer_layout?.closeDrawer(GravityCompat.START)

        when (item.id) {
            1 -> {
                Toast.makeText(this, "handleUpgrade", Toast.LENGTH_SHORT).show()
//                handleUpgrade()
            }
            2 -> {
                Toast.makeText(this, "rateUs", Toast.LENGTH_SHORT).show()

//                rateUs()
            }
            3 -> {
                Toast.makeText(this, "shareApp", Toast.LENGTH_SHORT).show()

//                shareApp()
            }
            4 -> {
                Toast.makeText(this, "handlesettings", Toast.LENGTH_SHORT).show()

//                handlesettings()
            }

        }
    }
}