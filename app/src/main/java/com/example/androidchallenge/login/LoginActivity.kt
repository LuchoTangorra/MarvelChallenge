package com.example.androidchallenge.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidchallenge.mainScreen.MainScreenActivity
import com.example.androidchallenge.R
import com.example.androidchallenge.utils.Utils
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()
        startAuth()
    }

    private fun startAuth() {
        if (!userAlreadySignedIn()) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTheme(R.style.AppTheme)
                    .build(),
                RC_SIGN_IN
            )
        } else {
            navigateToNextActivity()
        }
    }

    private fun navigateToNextActivity() {
        val i = Intent(this, MainScreenActivity::class.java)
        startActivity(i)
    }

    private fun userAlreadySignedIn(): Boolean {
        val auth = FirebaseAuth.getInstance()
        auth.currentUser?.let {
            return true
        } ?: run {
            return false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                navigateToNextActivity()
            }
        }
    }

}