
package com.sg.bookappfirebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sg.bookappfirebase.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginBtn_main.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        skipBtn_main.setOnClickListener {
            val intent=Intent(this, DashboardUserActivity::class.java)
            startActivity(intent)

        }
    }
}