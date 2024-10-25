package com.example.findyourfamily.view



import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findyourfamily.R
import com.example.findyourfamily.databinding.ActivityMainBinding
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {


    lateinit var actionDrawerToggle: ActionBarDrawerToggle

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomBar.setupWithNavController(navController)
        binding.drawerNav.setupWithNavController(navController)

        actionDrawerToggle = ActionBarDrawerToggle(this, binding.drawerlayout, R.string.nav_open, R.string.nav_close)

        actionDrawerToggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.drawerNav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    com.google.firebase.Firebase.auth.signOut()
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
                R.id.profileFragment2 -> {
                    navController.navigate(R.id.profileFragment2)
                }
                R.id.friendFragment2 -> {
                    navController.navigate(R.id.friendsFragment2)
                }

            }
            true
        }
        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    com.google.firebase.Firebase.auth.signOut()
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
                R.id.profileFragment2 -> {
                    navController.navigate(R.id.profileFragment2)
                }
                R.id.friendFragment2 -> {
                    navController.navigate(R.id.friendsFragment2)
                }

            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}




