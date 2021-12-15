package com.sg.bookappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashbooard_admin.*

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbooard_admin)
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        logoutBtn__Dashboard_admin.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        addCategoryBtn_Dash_admin.setOnClickListener {
            startActivity(Intent(this,CategoryAddActivity::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if (firebaseUser==null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{
            val email=firebaseUser.email
            subTitle_Dashboard_admin.text=email
        }
    }
}