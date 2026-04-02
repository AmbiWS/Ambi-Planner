package com.ambiws.ambiplanner.features.dashboard.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.base.BaseHostFragment
import com.ambiws.ambiplanner.base.navigation.BottomNavigationController
import com.ambiws.ambiplanner.databinding.FragmentDashboardBinding
import com.ambiws.ambiplanner.features.dashboard.ui.navbar.BottomItem
import com.ambiws.ambiplanner.utils.extensions.getCurrentFragment
import com.ambiws.ambiplanner.utils.extensions.onBackPressedCallback
import com.ambiws.ambiplanner.utils.extensions.subscribe

class DashboardFragment : BaseHostFragment<DashboardViewModel, FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private lateinit var bottomNavigationController: BottomNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        initCallbacks()
        binding.bottomNavbar.setActiveTab(BottomItem.HOME)
    }

    private fun initCallbacks() {
        onBackPressedCallback {
            when (binding.bottomNavbar.currentActiveTab) {
                BottomItem.SETTINGS -> {
                    binding.bottomNavbar.setActiveTab(binding.bottomNavbar.prevActiveTab)
                }
                else -> {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setupBottomNavController() {
        bottomNavigationController = BottomNavigationController(
            bottomGraphs = listOf(
                BottomNavigationController.BottomGraph(
                    BottomItem.HOME,
                    R.navigation.navigation_home,
                    R.id.navigation_home
                ),
                BottomNavigationController.BottomGraph(
                    BottomItem.CALENDAR,
                    R.navigation.navigation_calendar,
                    R.id.navigation_calendar
                ),
                BottomNavigationController.BottomGraph(
                    BottomItem.SETTINGS,
                    R.navigation.navigation_settings,
                    R.id.navigation_settings
                )
            ),
            fragmentManager = childFragmentManager,
            containerId = R.id.dashboardContainer
        )
    }

    private fun initBottomNavigation() {
        viewModel.connectBottomNavController(
            bottomNavigationController.setup(
                bottomNavigationView = binding.bottomNavbar
            )
        )
    }

    override fun setupObservers() {
        super.setupObservers()
        subscribe(viewModel.bottomBarVisible) {
            binding.bottomNavbar.isVisible = it
        }
        subscribe(viewModel.selectedTab) {
            binding.bottomNavbar.setActiveTab(it)
        }
    }

    override fun getCurrentFragment(): Fragment? =
        (childFragmentManager.findFragmentById(R.id.dashboardContainer) as? NavHostFragment)?.getCurrentFragment()
}
