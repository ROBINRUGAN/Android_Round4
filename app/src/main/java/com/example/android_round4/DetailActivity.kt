package com.example.android_round4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.android_round4.Myapplication.Companion.UserData
import com.example.android_round4.entity.Contribution
import com.example.android_round4.entity.Register
import com.example.android_round4.ui.me.MeFragment
import com.example.android_round4.util.appContext
import jsonOf
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailTitle.text=intent.getStringExtra("project.title")
        detailInfo.text=intent.getStringExtra("project.content")
        val price="已筹资: "+intent.getIntExtra("project.price",0)+"元"
        detailMoney.text=price.toString()
        detailTel.text="电话: "+intent.getStringExtra("project.telephone")
        Glide.with(this).load(intent.getStringExtra("project.imageurl")).into(detailImage)
        contributeBtn.setOnClickListener{
            if(UserData==null)
            {
                Toast.makeText(this, "你还没有登录！！！", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("id","1")
                this.startActivity(intent)
            }
            else
            {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://money.mewtopia.cn:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val appService = retrofit.create(AppService::class.java)
                Log.d("meww", "我进来else了")
                val map = jsonOf(
                    "money" to detailInput.text.toString(),
                    "id" to UserData!!.data.user.id.toString(),
                    "itemId" to intent.getIntExtra("project.id",0).toString()
                )
                Log.d("meww出资的钱",detailInput.text.toString())
                Log.d("meww用户id", intent.getIntExtra("user.id",0).toString())
                Log.d("meww项目id", intent.getIntExtra("project.id",0).toString())
                appService.getcontribution(map).enqueue(object : Callback<Contribution> {
                    override fun onResponse(call: Call<Contribution>, response: Response<Contribution>) {
                        val contributionData = response.body()
                        if (contributionData != null)
                            Toast.makeText(appContext, contributionData.msg, Toast.LENGTH_SHORT).show()
                        else
                            Log.d("meww", "wrong")
                    }

                    override fun onFailure(call: Call<Contribution>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }

        }
    }
}
