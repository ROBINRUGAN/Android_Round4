package com.example.android_round4.entity

data class Login(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val token: String,
    val user: User,
    val records: String
)

data class User(
    val email: String,
    val id: Int,
    val money: Int,
    val name: String,
    val password: String,
    val status: Int
)
data class Register(
    val code: Int,
    val `data`: Any,
    val msg: String
)
class ProjectList : ArrayList<ProjectListItem>()

data class ProjectListItem(
    val content: String,
    val id: Int,
    val imageurl: String,
    val price: Int,
    val status: Int,
    val telephone: String,
    val title: String,
    val userid: Int
)
data class Add(
    val code: Int,
    val `data`: Data,
    val msg: String
)
data class Contribution(
    val code: Int,
    val `data`: String,
    val msg: String
)

data class Check(
    val code: Int,
    val `data`: String,
    val msg: String
)