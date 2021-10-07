package com.example.myapplication.ui.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

/*
        val sharedPref=this.getPreferences("autoLogin") ?:return
        with(sharedPref.edit()){
            putInt("key",1)
            apply()
        }*/

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("autoLogin", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("key", 1)
        editor.apply()


        val bottomNavigation=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navigationHost.navController


        bottomNavigation.setupWithNavController(navController)



    }
    private var backPressedTime:Long = 0
    private lateinit var backToast: Toast
    override fun onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}