package com.example.android_round4;
import android.app.Application;
import com.example.android_round4.entity.Login
import com.example.android_round4.entity.Project

class Myapplication :Application(){
  companion object{
   lateinit var application: Application
   var UserData: Login? =null
        }
     init {
         application = this
     }
}
