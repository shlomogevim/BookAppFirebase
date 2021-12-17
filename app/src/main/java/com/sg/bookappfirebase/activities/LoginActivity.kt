package com.sg.bookappfirebase.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sg.bookappfirebase.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    var email = ""
    var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait ...")
        progressDialog.setCanceledOnTouchOutside(false)

        noAccountTv__login.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginBtn_login.setOnClickListener {
            validateData()

        }


    }

    private fun validateData() {
        email = emailEt_login.text.toString().trim()
        password = passwordEt__login.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Wrong email format", Toast.LENGTH_LONG).show()
        } else {
            if (password.isEmpty()) {
                Toast.makeText(this, "password is empty", Toast.LENGTH_LONG).show()
            } else {
                loginUser()
            }
        }

    }

    private fun loginUser() {
        progressDialog.setMessage("Logging In ....")
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener {
                Toast.makeText(this, "cannot sign in because:->${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }

    private fun checkUser() {
        progressDialog.setMessage("Checking User ....")
        val firebaseUser=firebaseAuth.currentUser!!
        val ref=FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    val userType=snapshot.child("userType").value
                    if (userType=="user"){
                        startActivity(Intent(this@LoginActivity, DashboardUserActivity::class.java))
                       finish()
                    }else if (userType=="admin"){
                        startActivity(Intent(this@LoginActivity, DashboardAdminActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
    }
}