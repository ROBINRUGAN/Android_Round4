package com.example.android_round4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_round4.adapter.AdminAdapter
import com.example.android_round4.adapter.ProjectAdapter
import com.example.android_round4.entity.Project
import com.example.android_round4.entity.ProjectList
import com.example.android_round4.util.appContext
import kotlinx.android.synthetic.main.activity_admin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminActivity : AppCompatActivity() {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://money.mewtopia.cn:8083/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val appService = retrofit.create(AppService::class.java)
    var projectList: ProjectList? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        allPass.setOnClickListener{
            allpass()
        }
        allFail.setOnClickListener{
            allfail()
        }
        allUnknown.setOnClickListener{
            allunknown()
        }
        allpass()

    }

    fun allpass() {
        appService.getprojectpasslist().enqueue(object : Callback<ProjectList> {
            override fun onResponse(call: Call<ProjectList>, response: Response<ProjectList>) {
                projectList = response.body()
                Log.d("我拿到的数据库数据", response.body().toString())
                val layoutManager = LinearLayoutManager(appContext)
                adminrecycleview.layoutManager = layoutManager
                Log.d("我要输出了？", projectList.toString())
                val adapter = AdminAdapter(projectList!!, this@AdminActivity)
                adminrecycleview.adapter = adapter
            }
            override fun onFailure(call: Call<ProjectList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun allunknown() {
        appService.getprojectunknownlist().enqueue(object : Callback<ProjectList> {
            override fun onResponse(call: Call<ProjectList>, response: Response<ProjectList>) {
                projectList = response.body()
                Log.d("我拿到的数据库数据", response.body().toString())
                val layoutManager = LinearLayoutManager(appContext)
                adminrecycleview.layoutManager = layoutManager
                Log.d("我要输出了？", projectList.toString())
                val adapter = AdminAdapter(projectList!!, this@AdminActivity)
                adminrecycleview.adapter = adapter
            }
            override fun onFailure(call: Call<ProjectList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun allfail() {
        appService.getprojectfaillist().enqueue(object : Callback<ProjectList> {
            override fun onResponse(call: Call<ProjectList>, response: Response<ProjectList>) {
                projectList = response.body()
                Log.d("我拿到的数据库数据", response.body().toString())
                val layoutManager = LinearLayoutManager(appContext)
                adminrecycleview.layoutManager = layoutManager
                Log.d("我要输出了？", projectList.toString())
                val adapter = AdminAdapter(projectList!!, this@AdminActivity)
                adminrecycleview.adapter = adapter
            }
            override fun onFailure(call: Call<ProjectList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}