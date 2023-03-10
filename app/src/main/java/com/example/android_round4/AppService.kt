package com.example.android_round4

import com.example.android_round4.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface AppService {

    @POST("login")
    @Headers("Content-Type: application/json")
    fun getlogin(@Body body: RequestBody): retrofit2.Call<Login>

    @POST("register")
    @Headers("Content-Type: application/json")
    fun getregister(@Body body: RequestBody): retrofit2.Call<Register>

    @POST("sys/item/listItem")
    fun getprojectlist(): retrofit2.Call<ProjectList>

    @POST("sys/item/add")

    @Multipart
    fun getadd(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("telephone") telephone: String,
        @Query("userid") userid: Int,
        @Part file: MultipartBody.Part
    ): retrofit2.Call<Add>

    @POST("sys/item/getmyitem/{id}")
    fun getprojectpersonallist(@Path("id") id:Int ): retrofit2.Call<ProjectList>

    @POST("sys/item/viewItem/0")
    fun getprojectunknownlist(): retrofit2.Call<ProjectList>

    @POST("sys/item/viewItem/-1")
    fun getprojectfaillist(): retrofit2.Call<ProjectList>

    @POST("sys/item/viewItem/1")
    fun getprojectpasslist(): retrofit2.Call<ProjectList>

    @POST("sys/user/contribution")
    fun getcontribution(@Body body: RequestBody): retrofit2.Call<Contribution>

    @POST("sys/item/verify")
    fun getcheck(@Body body: RequestBody): retrofit2.Call<Check>

    @POST("sys/item/deletelist")
    fun getdel(@Body body: RequestBody): retrofit2.Call<Check>

    @POST("sys/item/find")
    fun getsearchlist(@Query("title")title: String): retrofit2.Call<ProjectList>
}