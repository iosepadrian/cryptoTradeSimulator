package com.example.myapplication.ui.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.data.data.model.InvestedCoin
import com.example.myapplication.modelView.FavCoinModelView
import com.example.myapplication.modelView.UserViewModel
import com.example.myapplication.repository.database.InvestedCoinDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        val viewModel=ViewModelProvider(this).get(FavCoinModelView::class.java)
        val viewModel2=ViewModelProvider(this).get(UserViewModel::class.java)
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("autoLogin", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("key", 1)
        editor.apply()
        val dbInvested= InvestedCoinDatabase.getDatabase(applicationContext)
        if(dbInvested.investedCoinDao().load()==null){
            dbInvested.investedCoinDao().insert(
                InvestedCoin("USDT","Tether","USDT","https://assets.coingecko.com/coins/images/325/large/Tether-logo.png?1598003707",
                    "3","0", 100000.0)
            )
        }

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navigationHost.navController


        bottomNavigation.setupWithNavController(navController)



    }
    private var backPressedTime:Long = 0
    private lateinit var backToast: Toast
    override fun onBackPressed() {

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

        if (backPressedTime + 1000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        }
        backPressedTime = System.currentTimeMillis()
        Toast.makeText(this,"Press back again to exit app",Toast.LENGTH_SHORT).show()
    }
}