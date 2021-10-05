package com.huda.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huda.myapplication.model.ResultResponse
import com.huda.myapplication.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_post_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PostImageActivity : AppCompatActivity() {
    private var pathFoto : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_image)
        btn_pilih.setOnClickListener {
            pickImage()
        }
        btn_kirim.setOnClickListener {
            if("" === pathFoto){
                Toast.makeText(this, "Foto Belum Dipilih", Toast.LENGTH_LONG).show()
            }else{
                val file = File(pathFoto)
                val requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val part = MultipartBody.Part.createFormData("uploaded_file", file.toString(), requestFile)
                RetrofitClient.instance.postImage(part)
                    .enqueue(object : retrofit2.Callback<ResultResponse?> {
                        override fun onResponse(
                            call: Call<ResultResponse?>?,
                            response: Response<ResultResponse?>?
                        ) {
                            if (response!!.isSuccessful){
                                Toast.makeText(this@PostImageActivity, response.body()!!.result, Toast.LENGTH_LONG).show()
                                Toast.makeText(this@PostImageActivity, file.name, Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<ResultResponse?>?, t: Throwable?) {
                            Toast.makeText(this@PostImageActivity, "Gagal Kirim :  $t", Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
    }

    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra("crop", "true")
            intent.putExtra("scale", true)
            intent.putExtra("aspectX", 16)
            intent.putExtra("aspectY", 9)
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return
            }
            val uri = data?.data
            if (uri != null) {
                val imageFile = uriToImageFile(uri)
                val bitmapImage = BitmapFactory.decodeFile(imageFile.toString())
                iv_foto.setImageBitmap(bitmapImage)
            }
            if (uri != null) {
                val imageBitmap = uriToBitmap(uri)
                iv_foto.setImageBitmap(imageBitmap)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    pickImage()
                }
            }
        }
    }
    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                pathFoto = filePath
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }
    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }
    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }
}
