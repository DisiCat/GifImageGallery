package com.example.gifimagegallery.extensions

import com.example.gifimagegallery.entity.GifItemView
import com.example.gifimagegallery.network.parseModels.GifModel

internal fun GifModel.toGifItemView() : GifItemView{
    return GifItemView(
        id = this.id,
        name = this.title,
        url = images?.original?.url
    )
}