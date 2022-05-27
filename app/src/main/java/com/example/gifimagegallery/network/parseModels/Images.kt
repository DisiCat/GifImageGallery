package com.example.gifimagegallery.network.parseModels

import com.squareup.moshi.Json

data class Images(
    @Json(name = "fixed_height")
    val fixedHeight: FixedHeight
)
