package com.example.myapplication.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.modelView.UserViewModel
import com.example.myapplication.data.data.model.User
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adiTag="AdiTag"
        val model=ViewModelProvider(this).get(UserViewModel::class.java)

        val sharedpreferences = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val j: Int = sharedpreferences.getInt("key", 0)

        if(model.loadAllUsers().isNotEmpty())
        {
            if(j > 0)
            {
                model.user.observe(this, {
                    findViewById<EditText>(R.id.usernameEditText).setText(it.username)
                })
            }
        }


        /*if(model.loadAllUsers().isNotEmpty()) {
            model.user.observe(this, {
                findViewById<EditText>(R.id.usernameEditText).setText(it.username)
            })
        }*/
        if(model.loadAllUsers().isNotEmpty()) {
            for (user in model.loadAllUsers()) {
                print(Log.v(adiTag, user.toString()))
            }
        }
        val editText=findViewById<EditText>(R.id.usernameEditText)
        val editText2=findViewById<EditText>(R.id.passwordEditText)

        val loginButton= findViewById<Button>(R.id.signInButton)
        loginButton.setOnClickListener{

            loginButton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce))
            val username:String = editText.text.toString()
            val password:String = editText2.text.toString()
            if(username!="" && password!="") {

                if (model.user.value?.username.equals(username)) {
                    if (model.user.value?.password.equals(password)) {
                        Toast.makeText(
                            applicationContext,
                            "Authentication succeeded!", Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent(this@MainActivity, UserActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect password!", Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    if(model.loadAllUsers().isNotEmpty()) {
                        model.deleteUser(model.user.value)
                    }
                    val userToAdd = User("0", username, password)
                    model.insertUser(userToAdd)
                    val i = Intent(this@MainActivity, UserActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }else {
                Toast.makeText(
                    applicationContext,
                    "Complete imputs", Toast.LENGTH_SHORT
                )
                    .show()
            }


        } // end of clickListener



        //set fingerpring login
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    if(model.loadAllUsers().isNotEmpty()) {
                        Toast.makeText(applicationContext,
                            "Authentication succeeded!", Toast.LENGTH_SHORT)
                            .show()
                        val i = Intent(this@MainActivity, UserActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,
                            "There is no user in database!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint login")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Use account password")
            .build()
        val fingerprintLogin= findViewById<ImageView>(R.id.fingerprintImage)
        fingerprintLogin.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }// end of fingerprint logi


        val constraintLayout=findViewById<ConstraintLayout>(R.id.constraintLogin)
        constraintLayout.setOnClickListener {
            // hide soft keyboard on rot layout click
            // it hide soft keyboard on edit text outside root layout click
            hideSoftKeyboard()

            // remove focus from edit text
            editText.clearFocus()
            editText2.clearFocus()
        }



    }

    private fun Activity.hideSoftKeyboard(){
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

}