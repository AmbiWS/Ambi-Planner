package com.ambiws.ambiplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ambiws.ambiplanner.databinding.ActivityMainBinding
import com.ambiws.ambiplanner.utils.extensions.subscribe

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initViewBinding()
        initNavigation()
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initNavigation() {
        if (getApplicationContainerFragment() != null) {
            startNavigationFlow()
        } else {
            initNavigationGraph()
        }
    }

    private fun startNavigationFlow() {
        getApplicationContainerFragment()?.let { navigationHostFragment ->
            initDashboardNavigation(navigationHostFragment)
            activateSplashScreen()
        } ?: throw IllegalStateException(getString(R.string.exception_null_app_container_fragment))
    }

    private fun initNavigationGraph() {
        mainViewModel.initStartDestination()
        subscribe(mainViewModel.startDestinationEvent) { destination ->
            val navigationHostFragment = NavHostFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.applicationContainerLayout, navigationHostFragment)
                .setPrimaryNavigationFragment(navigationHostFragment)
                .commitNow()

            val navigationGraph =
                navigationHostFragment.navController.navInflater.inflate(R.navigation.navigation_main)
            navigationGraph.setStartDestination(destination)
            navigationHostFragment.navController.setGraph(navigationGraph, null)

            startNavigationFlow()
        }
    }

    private fun initDashboardNavigation(navigationHostFragment: Fragment) {
        navigationHostFragment.findNavController()
            .addOnDestinationChangedListener { _, _, _ ->
                // TODO Navigation for dashboard
            }
    }

    private fun activateSplashScreen() {
        // TODO Custom splash screen animation
    }

    private fun getApplicationContainerFragment() =
        supportFragmentManager.findFragmentById(R.id.applicationContainerLayout)
}
