package com.example.bottomnavigationsample

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.bottomnavigationsample.databinding.ActivityMainBinding
import com.example.bottomnavigationsample.ui.dialog.NormalDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupBackConfirm()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
        // `setupWithNavController` set it, but we need analytics event
        binding.navView.setOnItemSelectedListener { item ->
            Log.d(MainActivity::class.java.simpleName, "on click ${item.title}")
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBackConfirm() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    NormalDialogFragment("Are you sure to close the app?") {
                        finish()
                    }.show(supportFragmentManager, NormalDialogFragment::getTag.name)
                }
            }
        onBackPressedDispatcher.addCallback(this, callback)
        navController.addOnDestinationChangedListener { controller, _, _ ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(controller.currentDestination?.id)
            callback.isEnabled = isTopLevelDestination
        }
    }
}