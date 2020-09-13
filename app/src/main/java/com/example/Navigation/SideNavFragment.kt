package com.example.Navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testopencv.R
import kotlinx.android.synthetic.main.fragment_sidenav.*

class SideNavFragment :Fragment(){

    private val sideNavAdapter: SideNavAdapter = SideNavAdapter {
            position, item ->
        onItemClick(position,item)
    }

    private fun onItemClick(position: Int, item: SideNavItem) {
        Toast.makeText(requireContext(),""+item.itemName,Toast.LENGTH_LONG).show()
        (activity as NavigationActivity).closeDrawer(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_sidenav, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        rv_side_nav_options?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        rv_side_nav_options?.adapter = sideNavAdapter
        sideNavAdapter.setNavItemsData(prrepareNavItems())
    }

    //Create List of items to be displayed on the sidenav
    private fun prrepareNavItems(): List<SideNavItem> {
        val menuItemsList = ArrayList<SideNavItem>()
        menuItemsList.add(SideNavItem(1,"Upgrade", R.drawable.ic_upgrade))
        menuItemsList.add(SideNavItem(2,"Rate us",R.drawable.ic_rate))
        menuItemsList.add(SideNavItem(3,"Share",R.drawable.ic_share))
        menuItemsList.add(SideNavItem(5,"Settings",R.drawable.ic_settings))
        return menuItemsList
    }
}