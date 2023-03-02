package com.example.android_round4

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.android_round4.Myapplication.Companion.UserData
import com.example.android_round4.entity.Add
import com.example.android_round4.ui.me.MeFragment
import com.example.fund.utils.URIPathHelper
import jsonOf
import kotlinx.android.synthetic.main.activity_add.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class AddActivity : AppCompatActivity() {
    lateinit var part: MultipartBody.Part
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        projectImageBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type="image/*"
            startActivityForResult(intent,2)
        }
        projectSubmitBtn.setOnClickListener{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8083/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(AppService::class.java)
            Log.d("meww", "我进来else了")
            appService.getadd(projectTitle.text.toString(),projectContent.text.toString(),projectTel.text.toString(),UserData!!.data.user.id,part).enqueue(object:Callback<Add>{
                override fun onResponse(call: Call<Add>, response: Response<Add>) {
                    Log.d("上传图片数据", response.body().toString())
                    Toast.makeText(this@AddActivity, "项目上传成功！！！", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddActivity,MainActivity::class.java)
                    this@AddActivity.startActivity(intent)
                }

                override fun onFailure(call: Call<Add>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> {
                Toast.makeText(this, "图片上传成功", Toast.LENGTH_SHORT).show()
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        //将选择的图片显示
                        val bitmap = getBitmapFromUri(uri)
                        val picture: ImageView = projectImage
                        picture.setImageBitmap(bitmap)
                        //获取真实路径
                        val helper = URIPathHelper()
                        val path = helper.getPath(this, uri)
                        //转成MultipartBody.Part
                        val file = File(path.toString())
                        val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                        part = MultipartBody.Part.createFormData("file", file.name, requestBody)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            return BitmapFactory.decodeFileDescriptor(parcelFileDescriptor!!.fileDescriptor)
    }

}