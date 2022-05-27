package com.example.gifimagegallery.network.parseModels

import com.squareup.moshi.Json

data class Meta(
    val status: Long,
    val msg: String,
    @Json(name = "response_id")
    val responseID: String
)
