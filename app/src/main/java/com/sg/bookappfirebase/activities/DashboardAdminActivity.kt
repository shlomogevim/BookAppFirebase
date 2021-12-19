package com.sg.bookappfirebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.sg.bookappfirebase.*
import com.sg.bookappfirebase.R
import com.sg.bookappfirebase.adapters.CategAdapter
import com.sg.bookappfirebase.interfaces.CategoryInterface
import com.sg.bookappfirebase.model.Cat
import com.sg.bookappfirebase.adapters.categoryAdapter
import com.sg.bookappfirebase.model.Categ
import kotlinx.android.synthetic.main.activity_dashbooard_admin.*
import java.util.*

class DashboardAdminActivity : AppCompatActivity(), CategoryInterface {

    private val catRef = FirebaseFirestore.getInstance().collection(CATEGORY_REF)
    private lateinit var categoryAdapter: CategAdapter
    private var categories = arrayListOf<Categ>()
    lateinit var categoryListener: ListenerRegistration
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbooard_admin)
        auth = FirebaseAuth.getInstance()

        checkUser()

        categoryAdapter = CategAdapter(categories, this)

        categoriesRv_Dashboard_admin.adapter = categoryAdapter
        categoriesRv_Dashboard_admin.layoutManager = LinearLayoutManager(this)

        searchEt_Dashboard_admin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        })



        logoutBtn__Dashboard_admin.setOnClickListener {
            auth.signOut()
            checkUser()
            //loadCategories()

            /* searchEt_Dashboard_admin.addTextChangedListener(object :TextWatcher{
                 override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                     TODO("Not yet implemented")
                 }

                 override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                     try {
                         categoryAdapter.filter.filter(s)

                     }
                     catch (e:Exception){

                     }
                 }

                 override fun afterTextChanged(p0: Editable?) {
                     TODO("Not yet implemented")
                 }


             })*/
        }
        addCategoryBtn_Dash_admin.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        setListener()
    }

    private fun setListener() {
        categoryListener = catRef
            .orderBy(CATEGORY_TIMESTAMP, Query.Direction.DESCENDING)
            .addSnapshotListener(this) { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(
                        this,
                        "exception in loading categoryList-> ${exception.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (snapshot != null) {
                    parseData(snapshot)
                }
            }
    }

    private fun parseData(snapshot: QuerySnapshot) {

        var cateroryName = ""

        categories.clear()
        for (document in snapshot.documents) {
            val data = document.data
            if (data != null) {
                if (data[CATEGORY_NAME] != null) {
                    cateroryName = data[CATEGORY_NAME] as String
                }
                val timestamp=document.getTimestamp(CATEGORY_TIMESTAMP)
                val documentID=document.id

                val newCategory = Categ(cateroryName,timestamp,documentID)
                categories.add(newCategory)
            }
            categoryAdapter.notifyDataSetChanged()
        }
    }


    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val email = firebaseUser.email
            subTitle_Dashboard_admin.text = email
        }
    }

    override fun categoryInterfaceListenet(category: Categ) {

        val categoryRef=FirebaseFirestore.getInstance().collection(CATEGORY_REF).document(category.documentID)

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.option_menu, null)
        val deleteBtn = dialogView.findViewById<Button>(R.id.optionDeleteBtn)
        val editBtn = dialogView.findViewById<Button>(R.id.optionEditBtn)
        builder.setView(dialogView)
            .setNegativeButton("Cancel") { _, _ -> }
        val ad = builder.show()

        deleteBtn.setOnClickListener {
            categoryRef.delete()
                .addOnSuccessListener {
                    ad.dismiss()
                }
                .addOnFailureListener {
                    Log.d(TAG,"cannot delete category because -> ${it.localizedMessage}")
                }
            setListener()
        }


    }

   


    /*class DashboardAdminActivity : AppCompatActivity(), CategoryInterface {

    private val catRef = FirebaseFirestore.getInstance().collection(CATEGORY_REF_OLD)
    private lateinit var categoryAdapter: categoryAdapter
    private var categories = arrayListOf<Cat>()
    lateinit var categoryListener: ListenerRegistration
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbooard_admin)
        auth = FirebaseAuth.getInstance()

        checkUser()

        categoryAdapter = categoryAdapter(categories, this)

        categoriesRv_Dashboard_admin.adapter = categoryAdapter
        categoriesRv_Dashboard_admin.layoutManager = LinearLayoutManager(this)

        searchEt_Dashboard_admin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        })



        logoutBtn__Dashboard_admin.setOnClickListener {
            auth.signOut()
            checkUser()
            //loadCategories()

            /* searchEt_Dashboard_admin.addTextChangedListener(object :TextWatcher{
                 override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                     TODO("Not yet implemented")
                 }

                 override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                     try {
                         categoryAdapter.filter.filter(s)

                     }
                     catch (e:Exception){

                     }
                 }

                 override fun afterTextChanged(p0: Editable?) {
                     TODO("Not yet implemented")
                 }


             })*/
        }
        addCategoryBtn_Dash_admin.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        setListener()
    }

    private fun setListener() {
        categoryListener = catRef
            .orderBy(CATEGORY_TIMESTAMP_OLD, Query.Direction.DESCENDING)
            .addSnapshotListener(this) { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(
                        this,
                        "exception in loading categoryList-> ${exception.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (snapshot != null) {
                    parseData(snapshot)
                }
            }
    }

    private fun parseData(snapshot: QuerySnapshot) {

        var id = ""
        var category = ""
        var uid = ""
        var timestamp = 0L

        categories.clear()
        for (document in snapshot.documents) {
            val data = document.data
            if (data != null) {
                if (data[CATEGORY_ID_OLD] != null) {
                    id = data[CATEGORY_ID_OLD] as String
                }
                if (data[CATEGORY_CATEGORY_OLD] != null) {
                    category = data[CATEGORY_CATEGORY_OLD] as String
                }
                if (data[CATEGORY_TIMESTAMP_OLD] != null) {
                    timestamp = data[CATEGORY_TIMESTAMP_OLD] as Long
                }


                if (data[CATEGORY_UID_OLD] != null) {
                    uid = data[CATEGORY_UID_OLD] as String
                }
                val newCategory = Cat(id, category, timestamp, uid)
                categories.add(newCategory)
            }
            categoryAdapter.notifyDataSetChanged()
        }
    }


    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val email = firebaseUser.email
            subTitle_Dashboard_admin.text = email
        }
    }

    override fun categoryInterfaceListenet(category: Cat) {


        val catId = catRef.document(category.id)
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.option_menu, null)
        val deleteBtn = dialogView.findViewById<Button>(R.id.optionDeleteBtn)
        val editBtn = dialogView.findViewById<Button>(R.id.optionEditBtn)
        builder.setView(dialogView)
            .setNegativeButton("Cancel") { _, _ -> }
        val ad = builder.show()
        deleteBtn.setOnClickListener {
            catId.delete()
                .addOnSuccessListener {
                    ad.dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "cannot delete category because -> ${it.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
         setListener()
        }

    }

    private fun deleteCollection(
        collection: CollectionReference,
        category: Cat,
        comlete: (Boolean) -> Unit
    ) {
        collection.get().addOnSuccessListener { snapshot ->

        }
    }
*/


    /* private fun loadCategories() {
         categories = ArrayList()
         val ref = FirebaseDatabase.getInstance().getReference(CATEGORY_REF_OLD)
         ref.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                 categories.clear()
                 for (ds in snapshot.children) {
                     val model = ds.getValue(Category::class.java)
                     categories.add(model!!)
                 }
                 categoryAdapter = categoryAdapter(categories)
                 categoriesRv_Dashboard_admin.adapter = categoryAdapter


             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }


         })
     }*/
}

/*class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var categories: ArrayList<ModelCategory>
    private lateinit var categoryAdapter: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbooard_admin)
        auth = FirebaseAuth.getInstance()
        checkUser()

        logoutBtn__Dashboard_admin.setOnClickListener {
            auth.signOut()
            checkUser()
            loadCategories()
            searchEt_Dashboard_admin.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    try {
                        categoryAdapter.filter.filter(s)

                    }
                    catch (e:Exception){

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }


            })
        }
        addCategoryBtn_Dash_admin.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity::class.java))
        }
    }

    private fun loadCategories() {
        categories = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categories.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(ModelCategory::class.java)
                    categories.add(model!!)
                }
                categoryAdapter = AdapterCategory(categories)
                categoriesRv_Dashboard_admin.adapter = categoryAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val email = firebaseUser.email
            subTitle_Dashboard_admin.text = email
        }
    }
}*/