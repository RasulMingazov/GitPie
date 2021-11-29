package com.psychojean.gitpie.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.psychojean.gitpie.R
import com.psychojean.gitpie.databinding.ActivityMainBinding
import com.psychojean.gitpie.extensions.isAccessTokenExistsIntoASharedPreferences
import com.psychojean.gitpie.extensions.removeAllAccessTokensFromSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (this.isAccessTokenExistsIntoASharedPreferences()){
            graph.startDestination = R.id.profileFragment
        } else {
            graph.startDestination = R.id.signInFragment
        }

        navController = navHostFragment.navController
        navController.graph = graph
        bottom_navigation.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun hideBottomNav() {
        bottom_navigation.visibility = View.GONE
    }

    fun showBottomNav() {
        bottom_navigation.visibility = View.VISIBLE
    }
}