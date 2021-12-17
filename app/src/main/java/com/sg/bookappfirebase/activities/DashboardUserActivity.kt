package com.sg.bookappfirebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.sg.bookappfirebase.R
import kotlinx.android.synthetic.main.activity_dashboard_user.*

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)
        firebaseAuth= FirebaseAuth.getInstance()

        checkUser()

        logoutBtn__Dashboard_user.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser==null){
            subTitle_Dashboard_user.text="Not Logged In"
        }else{
            val email=firebaseUser.email
            subTitle_Dashboard_user.text=email
        }

    }
}