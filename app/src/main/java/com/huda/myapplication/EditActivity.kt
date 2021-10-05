package com.huda.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huda.myapplication.model.DefaultResponse
import com.huda.myapplication.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        edt_id.setText(intent.getStringExtra("ID"))
        edt_task_name.setText(intent.getStringExtra("NAME"))
        edt_task_date.setText(intent.getStringExtra("DATE"))
        edt_is_done.setText(intent.getStringExtra("IS"))
        btn_edit.setOnClickListener {
            RetrofitClient.instance.updateTAsk(edt_id.text.toString().trim(),edt_task_name.text.toString().trim(), edt_task_date.text.toString().trim(),
                edt_is_done.text.toString().trim(),"update_task")
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>?,
                        response: Response<DefaultResponse>?
                    ) {
                        if(response!!.isSuccessful){
                            if(response.body().status == 1){
                                Toast.makeText(this@EditActivity, "Berhasil Edit", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
                        Toast.makeText(this@EditActivity, "Response : ${t}", Toast.LENGTH_LONG).show()
                    }

                })
        }
    }
}
