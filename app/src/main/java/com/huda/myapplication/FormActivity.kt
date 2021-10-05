package com.huda.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huda.myapplication.model.DefaultResponse
import com.huda.myapplication.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        sharedPreferences = getSharedPreferences("CONTOH", Context.MODE_PRIVATE)
        btn_login.setOnClickListener {
            val editor : SharedPreferences.Editor? = sharedPreferences.edit()
            editor!!.putString("ID", edt_id.text.toString().trim())
            editor.putString("Task", edt_task_name.text.toString().trim())
            editor.apply()
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        btn_hapus.setOnClickListener {
            RetrofitClient.instance.deleteTAsk(edt_id.text.toString().trim(), "delete_task")
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>?,
                        response: Response<DefaultResponse>?
                    ) {
                        if (response!!.isSuccessful){
                            if (response.body().status == 1){
                                Toast.makeText(this@FormActivity, "Berhasil", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this@FormActivity, response.body().message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
                        Toast.makeText(this@FormActivity, "Response : ${t}", Toast.LENGTH_LONG).show()
                    }

                })
        }
        btn_edit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("ID", "10")
            intent.putExtra("NAME", "Belajar 2")
            intent.putExtra("DATE", "2021-09-24")
            intent.putExtra("IS", "1")
            startActivity(intent)
        }
        btn_kirim_task.setOnClickListener {
            RetrofitClient.instance.insertTAsk(edt_id.text.toString().trim(),edt_task_name.text.toString().trim(), edt_task_date.text.toString().trim(),
                edt_is_done.text.toString().trim(),"add_new_task")
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>?,
                        response: Response<DefaultResponse>?
                    ) {
                        if(response!!.isSuccessful){
                            if(response.body().status == 1){
                                edt_id.setText("")
                                edt_task_name.setText("")
                                edt_task_date.setText("")
                                edt_is_done.setText("")
                                Toast.makeText(this@FormActivity, "Berhasil Kirim", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this@FormActivity, "Gagal Kirim", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
                        Toast.makeText(this@FormActivity, "Response : ${t}", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}
