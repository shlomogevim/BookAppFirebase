package com.sg.bookappfirebase

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

        }
    }

    private fun validateData() {
        category = categoryEt_addCategory.text.toString().trim()
        if (category.isEmpty()) {
            Toast.makeText(this, "There is no Category", Toast.LENGTH_LONG).show()
        } else {
            addCategoryFirebase()
        }

    }

    private fun addCategoryFirebase() {
        progressDialog.show()
        val timestamp = System.currentTimeMillis()
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["category"] = category
        hashMap["timestap"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

     /* val data = HashMap<String, Any>()
        data.put(CATEGORY, selectedCategory)
        data.put(NUM_COMMENTS, 0)
        data.put(NUM_LIKES, 0)
        data.put(THOUGHT_TXT, addThoughtTxt.text.toString())
        data.put(TIMESTAMP, FieldValue.serverTimestamp())
        data.put(USERNAME, FirebaseAuth.getInstance().currentUser?.displayName.toString())
        data.put(USER_ID, FirebaseAuth.getInstance().currentUser?.uid.toString())

        FirebaseFirestore.getInstance().collection(THOUGHT_REF).add(data)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener {
                Log.e(TAG, "could not add post exception because --> ${it.message}")
            }

    }*/




        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Add category succssfully ....", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()

            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Cannot create Category because -> ${it.message}",
                    Toast.LENGTH_LONG
                ).show()


            }

    }
}