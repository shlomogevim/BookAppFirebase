package com.sg.bookappfirebase.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.bookappfirebase.*
import kotlinx.android.synthetic.main.activity_category_add.*

class CategoryAddActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_add)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(" Please wait ....")
        progressDialog.setCanceledOnTouchOutside(false)

        backBtn_addCategory.setOnClickListener {
            onBackPressed()
        }
        submitBtn_addCategory.setOnClickListener {
            validateData()
            finish()
        }
    }

    private fun validateData() {
        category = categoryEt_addCategory.text.toString().trim()
        if (category.isEmpty()) {
            Toast.makeText(this, "There is no Category,please enter new one ...", Toast.LENGTH_LONG).show()
        } else {
            addCategoryFirebase()
        }

    }

    private fun addCategoryFirebase() {
        progressDialog.show()

        val data=HashMap<String,Any>()
        data.put(CATEGORY_NAME,category)
        data.put(CATEGORY_TIMESTAMP,FieldValue.serverTimestamp())
        FirebaseFirestore.getInstance().collection(CATEGORY_REF).add(data)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Log.d(TAG," Add post name ->$category}")

            }
            .addOnFailureListener {
                Log.d(TAG,"could not add post -> ${it.localizedMessage}")
            }













       /* progressDialog.show()
        val timestamp = System.currentTimeMillis()
        val data = HashMap<String, Any>()

        data.put(CATEGORY_ID_OLD,"$timestamp")
        data.put(CATEGORY_CATEGORY_OLD,category)
        data.put(CATEGORY_TIMESTAMP_OLD,timestamp)
        data.put(CATEGORY_UID_OLD,"${firebaseAuth.uid}")
        FirebaseFirestore.getInstance().collection(CATEGORY_REF_OLD).add(data)
            .addOnSuccessListener {
                progressDialog.dismiss()
            }
            .addOnFailureListener {
                Log.e(TAG, "could not add post exception because --> ${it.message}")
            }*/








    }
}