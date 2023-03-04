package com.example.android_round4.ui.me

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.convertTo
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android_round4.*
import com.example.android_round4.databinding.FragmentMeBinding
import com.example.android_round4.entity.Login
import com.example.android_round4.entity.Register
import com.example.android_round4.util.appContext
import com.google.gson.Gson
import jsonOf
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (Myapplication.UserData == null) {
            UserGone()
            MeShow()
        } else {
            UserShow()
            MeGone()
            binding.userCenterName.text = Myapplication.UserData!!.data.user.name
            binding.userCenterEmail.text = Myapplication.UserData!!.data.user.email
            binding.userCenterType.text = when (Myapplication.UserData!!.data.user.status) {
                0 -> {
                    binding.toAdminBtn.visibility=View.VISIBLE
                    "管理员"
                }
                else -> {
                    binding.toAdminBtn.visibility=View.GONE
                    "普通用户"
                }
            }
            binding.userCenterMoney.text = Myapplication.UserData!!.data.user.money.toString()
        }
        //登录操作
        binding.loginbtn.setOnClickListener() {
            if (binding.textView.text == "注册") {
                binding.textView.text = "登录"
                binding.email.visibility = View.GONE
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://money.mewtopia.cn:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val appService = retrofit.create(AppService::class.java)
                Log.d("meww", "我进来else了")
                //这里是传入用户名密码进行登录，yyy给的API发送参数全是json格式的
                val map = jsonOf(
                    "username" to binding.username.text.toString(),
                    "password" to binding.password.text.toString()
                )
                Log.d("meww", "我经过map了")
                appService.getlogin(map).enqueue(object : Callback<Login> {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        val loginData = response.body()
                        Log.d("meww", loginData.toString())
                        Log.d("meww", "我输入的账号是${binding.username.text}")
                        Log.d("meww", "我输入的密码是${binding.password.text}")
                        if (loginData != null) {
                            if (loginData.msg == "操作成功") {
                                Myapplication.UserData = loginData
                                Toast.makeText(appContext, "成功登录了捏", Toast.LENGTH_SHORT).show()
                                MeGone()
                                UserShow()
                                binding.userCenterName.text =
                                    Myapplication.UserData!!.data.user.name
                                binding.userCenterEmail.text =
                                    Myapplication.UserData!!.data.user.email
                                binding.userCenterType.text =
                                    when (Myapplication.UserData!!.data.user.status) {
                                        0 -> {
                                            binding.toAdminBtn.visibility=View.VISIBLE
                                            "管理员"
                                        }
                                        else -> {
                                            binding.toAdminBtn.visibility=View.GONE
                                            "普通用户"
                                        }
                                    }
                                binding.userCenterMoney.text =
                                    Myapplication.UserData!!.data.user.money.toString()

                            } else
                                Toast.makeText(appContext, loginData.msg, Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("meww", "wrong")
                        }
                    }

                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
        binding.registerbtn.setOnClickListener() {
            if (binding.textView.text == "登录") {
                binding.textView.text = "注册"
                binding.email.text=null
                binding.username.text=null
                binding.password.text=null
                binding.email.visibility = View.VISIBLE
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://money.mewtopia.cn:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val appService = retrofit.create(AppService::class.java)
                Log.d("meww", "我进来else了")
                val map = jsonOf(
                    "username" to binding.username.text.toString(),
                    "password" to binding.password.text.toString(),
                    "email" to binding.email.text.toString()
                )
                appService.getregister(map).enqueue(object : Callback<Register> {
                    override fun onResponse(call: Call<Register>, response: Response<Register>) {
                        val registerData = response.body()
                        if (registerData != null)
                            Toast.makeText(appContext, registerData.msg, Toast.LENGTH_SHORT).show()
                        else
                            Log.d("meww", "wrong")
                    }

                    override fun onFailure(call: Call<Register>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }

        binding.myProject.setOnClickListener(){
            val intent = Intent(context,PersonalActivity::class.java)
            this.startActivity(intent)
        }

        binding.toMeBtn.setOnClickListener()
        {
            UserGone()
            MeShow()
            binding.password.text = null
            Myapplication.UserData = null
        }
        binding.toAdminBtn.setOnClickListener()
        {
            val intent = Intent(context, AdminActivity::class.java)
            this.startActivity(intent)
        }
        binding.addBtn.setOnClickListener() {
            val intent = Intent(context, AddActivity::class.java)
            this.startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun MeGone() {
        binding.textView.visibility = View.GONE
        binding.username.visibility = View.GONE
        binding.password.visibility = View.GONE
        binding.loginbtn.visibility = View.GONE
        binding.registerbtn.visibility = View.GONE
    }

    fun MeShow() {
        binding.textView.visibility = View.VISIBLE
        binding.username.visibility = View.VISIBLE
        binding.password.visibility = View.VISIBLE
        binding.loginbtn.visibility = View.VISIBLE
        binding.registerbtn.visibility = View.VISIBLE
    }

    fun UserGone() {
        binding.userCenterEmail.visibility = View.GONE
        binding.userCenterMoney.visibility = View.GONE
        binding.userCenterName.visibility = View.GONE
        binding.userCenterType.visibility = View.GONE
        binding.yue.visibility = View.GONE
        binding.leixing.visibility = View.GONE
        binding.youxiang.visibility = View.GONE
        binding.toAdminBtn.visibility = View.GONE
        binding.toMeBtn.visibility = View.GONE
        binding.addBtn.visibility = View.GONE
        binding.myProject.visibility = View.GONE

    }

    fun UserShow() {
        binding.userCenterEmail.visibility = View.VISIBLE
        binding.userCenterMoney.visibility = View.VISIBLE
        binding.userCenterName.visibility = View.VISIBLE
        binding.userCenterType.visibility = View.VISIBLE
        binding.yue.visibility = View.VISIBLE
        binding.youxiang.visibility = View.VISIBLE
        binding.leixing.visibility = View.VISIBLE
        binding.toAdminBtn.visibility = View.VISIBLE
        binding.toMeBtn.visibility = View.VISIBLE
        binding.addBtn.visibility = View.VISIBLE
        binding.myProject.visibility = View.VISIBLE
    }
}