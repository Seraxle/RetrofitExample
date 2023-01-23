package com.example.retrofitexample

import com.example.retrofitexample.model.CharacterDetailsX
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GenshinAPI {

    @GET("characters")
    fun getCharacterNames(): Call<ArrayList<String>>

    @GET("characters/{path}")
    fun getCharacterDetails(@Path("path") characterName: String?): Call<CharacterDetailsX>
}