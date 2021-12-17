package com.sg.bookappfirebase.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sg.bookappfirebase.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog
    private var name = ""
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait ...")
        progressDialog.setCanceledOnTouchOutside(false)
        backBtnRegister.setOnClickListener {
            onBackPressed()
        }
        registerBtn_Register.setOnClickListener {
            validateData()

        }

    }

    private fun validateData() {
        name = nameEtRegister.text.toString().trim()
        email = emailEtRegister.text.toString()
        password = passwordEtRegister.text.toString().trim()
        val cPpassword = cPpasswordEtRegister.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name please ....", Toast.LENGTH_LONG).show()
        } else {
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email please ....", Toast.LENGTH_LONG).show()

            } else {
                if (password.isEmpty()) {
                    Toast.makeText(this, "Enter your password ", Toast.LENGTH_LONG).show()
                } else
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(this, "Invalid Email ....", Toast.LENGTH_LONG).show()

                    } else {
                        if (cPpassword.isEmpty()) {
                            Toast.makeText(this, "Confirm password", Toast.LENGTH_LONG).show()
                        } else {
                            if (password != cPpassword) {
                                Toast.makeText(this, "Password is not match", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                createUserAccount()
                            }
                        }
                    }
            }

        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account ...")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Cannot create User Acount1 -->${it.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()


            }
    }

    private fun updateUserInfo() {
        progressDialog.setTitle("Saving use information....")
        val timeStamp = System.currentTimeMillis()
        val uid = firebaseAuth.uid
        var hasMap: HashMap<String, Any?> = HashMap()
        hasMap["name"] = name
        hasMap["uid"] = uid
        hasMap["email"] = email
        hasMap["password"] = password
        hasMap["profileImage"] = ""
        hasMap["userType"] = "user"
        hasMap["timestamp"] = timeStamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hasMap)
            .addOnSuccessListener { input ->
                progressDialog.dismiss()
                Toast.makeText(this, "Create User Acount -->${input.toString()}", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, DashboardUserActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Cannot saving User Acount -->${it.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()

            }


    }
}