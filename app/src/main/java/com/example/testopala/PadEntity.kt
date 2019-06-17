package com.example.testopala

import com.google.gson.annotations.SerializedName

data class PadEntity(
    @SerializedName("color")
    val color: String,
    @SerializedName("pad")
    val pad: Int,
    @SerializedName("state")
    val state: Int,
    @SerializedName("time")
    val time: Double
)
