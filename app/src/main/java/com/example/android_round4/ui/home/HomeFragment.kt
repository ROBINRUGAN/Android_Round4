package com.example.android_round4.ui.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_round4.AddActivity
import com.example.android_round4.AppService
import com.example.android_round4.adapter.ProjectAdapter
import com.example.android_round4.databinding.FragmentHomeBinding
import com.example.android_round4.entity.Login
import com.example.android_round4.entity.Project
import com.example.android_round4.entity.ProjectList
import com.example.android_round4.entity.ProjectListItem
import com.example.android_round4.util.appContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var projectList: ProjectList? =null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8083/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.getprojectlist().enqueue(object : Callback<ProjectList> {
            override fun onResponse(call: Call<ProjectList>, response: Response<ProjectList>){
                projectList = response.body()
                Log.d("我拿到的数据库数据", response.body().toString())
                val layoutManager = LinearLayoutManager(appContext)
                binding.recycleview.layoutManager = layoutManager
                Log.d("我要输出了？", projectList.toString())
                val adapter = ProjectAdapter(projectList!!, this@HomeFragment)
                binding.recycleview.adapter = adapter
            }

            override fun onFailure(call: Call<ProjectList>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}