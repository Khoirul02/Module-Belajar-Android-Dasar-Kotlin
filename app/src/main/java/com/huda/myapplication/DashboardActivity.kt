package com.huda.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        sharedPreferences = getSharedPreferences("CONTOH", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("ID","")
        val task = sharedPreferences.getString("Task","")
        Toast.makeText(this, id, Toast.LENGTH_LONG).show()
        Toast.makeText(this, task, Toast.LENGTH_LONG).show()
        logout.setOnClickListener {
            val editor : SharedPreferences.Editor? = sharedPreferences.edit()
            editor!!.putString("ID", "")
            editor.putString("Task", "")
            editor.apply()
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }
}
