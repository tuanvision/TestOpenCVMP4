package com.example.FragmentViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.FragmentViewModel.ui.main.FragmentViewModelFragment
import com.example.testopencv.R

class FragmentViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_view_model_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentViewModelFragment.newInstance())
                .commitNow()
        }
    }
}