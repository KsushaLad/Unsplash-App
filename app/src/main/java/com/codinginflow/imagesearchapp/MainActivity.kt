package com.codinginflow.imagesearchapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    @SuppressLint("CommitTransaction", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment

        with(navHostFragment.findNavController()) { navController = this
        supportFragmentManager.findFragmentById((R.id.nav_host_fragment_main))}

        val appBarConfiguration = navController.let { it?.let { it1 -> AppBarConfiguration(it1.graph) } }
        appBarConfiguration?.let { navController.let { it1 -> it1?.let { it2 ->
            setupActionBarWithNavController(
                it2, it)
        } } }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return navController?.navigateUp() == true
    }
}