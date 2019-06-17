package com.example.testopala.retrofit

import com.example.testopala.PadEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PadService {

    @GET()
    fun list(@Url url: String) : Call<List<PadEntity>>

}