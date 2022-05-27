package com.example.gifimagegallery.network.parseModels

import com.squareup.moshi.Json

data class Pagination(
    @Json(name = "total_count")
    val totalCount: Long,
    val count: Long,
    val offset: Long
)